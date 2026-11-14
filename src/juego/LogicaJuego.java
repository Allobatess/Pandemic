package juego;

import datosObjetos.Ciudades;
import estructurasDatos.SinglyLinkedListQueue;
import juego.mazo.MazoInfeccion;
import juego.mazo.MazoJugador;
import mundo.Ciudad;
import mundo.Personaje;
import mundo.cartas.Carta;
import mundo.cartas.CartaInfeccion;

/**
 * Controlador principal del juego.
 * Gestiona el tablero de ciudades, el turno circular de los personajes, la tasa de infección,
 * el robo de cartas del mazo de jugador y la resolución de la mecánica de Epidemia.
 */
public class LogicaJuego {

    private final Ciudad[] tableroCiudades;
    private final Personaje[] jugadores;
    private int indiceJugadorActual; // Turno manejado sobre Arreglo Circular

    private final MazoInfeccion mazoInfeccion;
    private final MazoJugador mazoJugador;


    private int tasaInfeccion;
    private final Ciudad atlanta;

    // Brotes: con MAX_BROTES brotes los jugadores pierden
    private static final int MAX_BROTES = 8;
    private int contadorBrotes;

    public LogicaJuego() {
        // 1. Instanciar el tablero de 48 ciudades con sus conexiones y poblaciones
        this.tableroCiudades = Ciudades.crearCiudades();
        this.atlanta = buscarCiudad("Atlanta");

        // 2. Inicializar jugadores posicionados en Atlanta
        this.jugadores = new Personaje[]{
            new Personaje("Jugador 1", atlanta),
            new Personaje("Jugador 2", atlanta),
            new Personaje("Jugador 3", atlanta)
        };
        for (Personaje p : jugadores) {
            atlanta.agregarPersonaje(p);
        }
        this.indiceJugadorActual = 0;

        // 3. Inicializar Mazo de Infección y ejecutar preparación inicial (18 cubos)
        this.mazoInfeccion = new MazoInfeccion(tableroCiudades);
        this.mazoInfeccion.prepararInfeccionInicial();

        // 4. Configurar Mazo de Jugador creando mazo inicial sin epidemias con tal de poder hacer la repartición inicial
        this.mazoJugador = new MazoJugador();
        Carta[] mazoInicial = mazoJugador.crearMazoInicialSinEpidemias(tableroCiudades);
        mazoJugador.barajarArreglo(mazoInicial);

        // Repartir 3 cartas a cada uno de los 3 jugadores
        int cartasUsadas = mazoJugador.repartirManosIniciales(mazoInicial, jugadores);

        // Insertar 4 epidemias, barajar remanente y convertir a Stack
        mazoJugador.ensamblarYConvertirAStack(mazoInicial, cartasUsadas, 4);

        // 5. Configurar tasa de infección inicial en 2
        this.tasaInfeccion = 2;

        // 6. Contador de brotes en 0
        this.contadorBrotes = 0;
    }

    // ==========================================
    // MÉTODOS DE BÚSQUEDA Y TRASLADO
    // ==========================================

    public Ciudad buscarCiudad(String nombre) {
        for (Ciudad c : tableroCiudades) {
            if (c.obtenerNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public void cambiarUbicacion(Personaje personaje, Ciudad ciudadDestino) {
        Ciudad ciudadOrigen = personaje.obtenerCiudadActual();
        if (ciudadOrigen != null) {
            ciudadOrigen.removerPersonaje(personaje);
        }
        personaje.establecerCiudadActual(ciudadDestino);
        ciudadDestino.agregarPersonaje(personaje);
        System.out.println(personaje.obtenerNombre() + " se ha movido a " + ciudadDestino.obtenerNombre());
    }

    // ==========================================
    // DINÁMICA DEL TURNO Y RESOLUCIÓN DE EPIDEMIA
    // ==========================================

    public void ejecutarInfeccionAutomatica() {
    System.out.println("\n--- FASE DE INFECCIÓN AUTOMÁTICA ---");
    for (int i = 0; i < this.tasaInfeccion; i++) {
        CartaInfeccion robada = this.mazoInfeccion.robarCarta();
        if (robada != null) {
            infectarCiudad(robada.obtenerCiudad(), 1);
            if (hayDerrotaPorBrotes()) {
                return; // No tiene sentido seguir infectando: ya se perdió
            }
        }
    }
    }

    // ==========================================
    // INFECCIÓN Y BROTES
    // ==========================================

    /**
     * Agrega 'cantidad' cubos a la ciudad. Si la ciudad llega a tener 3 cubos y
     * todavía le faltan cubos por recibir, se produce un brote (una sola vez).
     */
    public void infectarCiudad(Ciudad ciudad, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            boolean hayBrote = ciudad.agregarCuboDeInfeccion();
            if (hayBrote) {
                resolverBrotes(ciudad);
                return;
            }
        }
        System.out.println("¡Infección! " + ciudad.obtenerNombre()
                + " ahora tiene " + ciudad.obtenerCubosDeInfeccion() + " cubo(s).");
    }

    /**
     * Resuelve un brote y todas las reacciones en cadena que provoque.
     * Recorre el grafo de ciudades en anchura (BFS) usando una cola:
     * cada ciudad que explota infecta a todas sus vecinas, y las vecinas que ya
     * tenían 3 cubos entran a la cola para explotar después.
     * Cada ciudad explota como máximo una vez por cadena.
     */
    private void resolverBrotes(Ciudad ciudadInicial) {
        SinglyLinkedListQueue<Ciudad> colaBrotes = new SinglyLinkedListQueue<>();
        Ciudad[] yaExplotaron = new Ciudad[tableroCiudades.length];
        int numExplotadas = 0;

        colaBrotes.enqueue(ciudadInicial);
        yaExplotaron[numExplotadas++] = ciudadInicial;

        while (!colaBrotes.estaVacia()) {
            Ciudad ciudad = colaBrotes.dequeue();
            contadorBrotes++;
            System.out.println("\n*** ¡BROTE en " + ciudad.obtenerNombre() + "! ***"
                    + " (Brotes: " + contadorBrotes + "/" + MAX_BROTES + ")");

            for (Ciudad vecina : ciudad.obtenerVecinos()) {
                // Una ciudad que ya explotó en esta cadena no vuelve a recibir cubos
                if (contiene(yaExplotaron, numExplotadas, vecina)) {
                    continue;
                }

                boolean vecinaExplota = vecina.agregarCuboDeInfeccion();
                if (vecinaExplota) {
                    System.out.println("   " + vecina.obtenerNombre()
                            + " ya tenía 3 cubos: ¡reacción en cadena!");
                    colaBrotes.enqueue(vecina);
                    yaExplotaron[numExplotadas++] = vecina;
                } else {
                    System.out.println("   " + vecina.obtenerNombre() + " recibe 1 cubo (ahora tiene "
                            + vecina.obtenerCubosDeInfeccion() + ").");
                }
            }
        }
    }

    private boolean contiene(Ciudad[] ciudades, int cantidad, Ciudad buscada) {
        for (int i = 0; i < cantidad; i++) {
            if (ciudades[i] == buscada) {
                return true;
            }
        }
        return false;
    }

    public boolean hayDerrotaPorBrotes() {
        return contadorBrotes >= MAX_BROTES;
    }

    public int obtenerContadorBrotes() {
        return contadorBrotes;
    }

    public boolean dinamicaTurno() {
        Personaje jugadorActual = jugadores[indiceJugadorActual];
        System.out.println("\n==========================================");
        System.out.println("TURNO DE: " + jugadorActual.obtenerNombre());
        System.out.println("==========================================");

        // Fase 1: Ejecutar 4 acciones interactivas del personaje
        jugadorActual.ejecutarAccion(this);

        // Fase 2: Robar 2 cartas del mazo de jugador
        System.out.println("\n--- Robando 2 cartas del mazo de jugador ---");
        for (int i = 0; i < 2; i++) {
            Carta robada = mazoJugador.robarCarta();
            if (robada != null) {
                System.out.println("Carta robada: " + robada.obtenerNombre() + " [" + robada.obtenerTipo() + "]");
                if (robada.obtenerTipo().equals("Epidemia")) {
                    resolverEpidemia();
                    if (hayDerrotaPorBrotes()) {
                        anunciarDerrotaPorBrotes();
                        return false;
                    }
                } else {
                    jugadorActual.agarrarCarta(robada);
                }
            } else {
                // CONDICIÓN DE DERROTA
                System.out.println("\n==========================================");
                System.out.println("¡¡¡ DERROTA !!! EL MAZO DE JUGADOR SE HA AGOTADO.");
                System.out.println("==========================================");
                return false; 
            }
        }

        // Fase 3: Infección automática basada en la tasa de infección actual
        ejecutarInfeccionAutomatica();
        if (hayDerrotaPorBrotes()) {
            anunciarDerrotaPorBrotes();
            return false;
        }
        
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        // Fase 4: Consulta opcional del mapa
            System.out.print("\n¿Desea consultar el estado del mapa actual? (s/n): ");
            
            String respuestaMapa = scanner.nextLine().trim();

            if (respuestaMapa.equalsIgnoreCase("s")) {
                imprimirEstadoMapa();
            }

        // Fase 5: Avanzar al siguiente jugador en el Circular Array
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.length;
        return true; // El juego continúa
        }

    private void anunciarDerrotaPorBrotes() {
        System.out.println("\n==========================================");
        System.out.println("¡¡¡ DERROTA !!! SE ALCANZARON " + MAX_BROTES + " BROTES.");
        System.out.println("==========================================");
    }

    public void imprimirEstadoMapa() {
        System.out.println("\n==========================================");
        System.out.println("              MAPA ACTUAL                 ");
        System.out.println("Brotes: " + contadorBrotes + "/" + MAX_BROTES
                + " | Tasa de infección: " + tasaInfeccion);
        System.out.println("==========================================");

        for (Ciudad ciudad : this.tableroCiudades) {
            int cubos = ciudad.obtenerCubosDeInfeccion();
            
            // Destacar si la ciudad está infectada
            if (cubos > 0) {
                System.out.println("!!! " + ciudad.obtenerNombre() 
                        + " (Cubos de infección: " + cubos + ") !!!");
            } else {
                System.out.println(ciudad.obtenerNombre() 
                        + " (Cubos de infección: " + cubos + ")");
            }
        }
    }

    public void resolverEpidemia() {
        System.out.println("\n!!! ALERTA DE EPIDEMIA !!!");

        // Step 1: Aumentar tasa de infección
        tasaInfeccion++;
        System.out.println("1. Tasa de infección incrementada a: " + tasaInfeccion);

        // Step 2: Infectar ciudad del descarte FIFO con 3 cubos (Regla simplificada)
        CartaInfeccion robadaDescarte = mazoInfeccion.extraerCartaDescarteFIFO();
        if (robadaDescarte != null) {
            Ciudad ciudadEpidemia = robadaDescarte.obtenerCiudad();
            System.out.println("2. Ciudad infectada desde mazo de descarte de infección: "
                    + ciudadEpidemia.obtenerNombre() + " (3 cubos).");
            infectarCiudad(ciudadEpidemia, 3); // Si ya tenía cubos, produce un brote
        } else {
            System.out.println("2. La pila de descartes de infección estaba vacía.");
        }
    }

    public Ciudad[] obtenerTableroCiudades() {
        return tableroCiudades;
    }

    public Personaje[] obtenerJugadores() {
        return jugadores;
    }

    public int obtenerTasaInfeccion() {
        return tasaInfeccion;
    }
}