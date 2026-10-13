package juego;

import datosObjetos.Ciudades;
import datosObjetos.InfeccionAutomatica;
import datosObjetos.MazoInfeccion;
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
    private final InfeccionAutomatica infeccionAutomatica;

    private int tasaInfeccion;
    private final Ciudad atlanta;

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
        this.infeccionAutomatica = new InfeccionAutomatica(mazoInfeccion);
        this.mazoInfeccion.prepararInfeccionInicial();

        // 4. Configurar Mazo de Jugador siguiendo el flujo de 5 pasos
        this.mazoJugador = new MazoJugador();
        Carta[] mazoInicial = mazoJugador.crearMazoInicialSinEpidemias(tableroCiudades);
        mazoJugador.barajarArreglo(mazoInicial);

        // Repartir 3 cartas a cada uno de los 3 jugadores
        int cartasUsadas = mazoJugador.repartirManosIniciales(mazoInicial, jugadores);

        // Insertar 4 epidemias, barajar remanente y convertir a Stack
        mazoJugador.ensamblarYConvertirAStack(mazoInicial, cartasUsadas, 4);

        // 5. Configurar tasa de infección inicial en 2
        this.tasaInfeccion = 2;
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
        infeccionAutomatica.infectar(tasaInfeccion);

        // Fase 4: Avanzar al siguiente jugador en el Circular Array
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.length;
        return true; // El juego continúa
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
            for (int i = 0; i < 3; i++) {
                ciudadEpidemia.agregarCuboDeInfeccion();
            }
            System.out.println("2. Ciudad infectada desde el descarte FIFO: " 
                    + ciudadEpidemia.obtenerNombre() + " (3 cubos asignados).");
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