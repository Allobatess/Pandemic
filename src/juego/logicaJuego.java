public class Juego {
    private CircularArray<Personaje> turnos;
    private Stack<Carta> mazoJugadores;
    private Stack<Carta> mazoInfeccion;

    private int curasDescubiertas;
    private int cubosDisponibles;
    private int tasaInfeccion;
    private boolean enEjecucion;

    // El constructor recibe los mazos ya instanciados y cargados por tus compañeros
    public Juego(Stack<Carta> mazoJugadores, Stack<Carta> mazoInfeccion) {
        this.turnos = new CircularArray<>(3);
        this.mazoJugadores = mazoJugadores;
        this.mazoInfeccion = mazoInfeccion;

        this.curasDescubiertas = 0;
        this.cubosDisponibles = 96;
        this.tasaInfeccion = 2;
        this.enEjecucion = true;
    }

    // Inicializa la partida recibiendo el nodo de Atlanta (creado externamente)
    public void iniciarJuego(Ciudad atlanta) {
        // Creación de personajes directo en la ciudad inicial (Atlanta)
        // Personaje p1 = new Personaje("Player1", atlanta);
        // Personaje p2 = new Personaje("Player2", atlanta);    crear los objetos se hace en datosObjeto
        // Personaje p3 = new Personaje("Player3", atlanta);

        // Repartir 3 cartas iniciales a cada jugador
        p1.inicializarMano(mazoJugadores);
        p2.inicializarMano(mazoJugadores);
        p3.inicializarMano(mazoJugadores);

        // Determinar primer turno en base a la carta con mayor población
        Personaje primero = determinarJugadorInicial(p1, p2, p3);
        configurarOrdenTurnos(primero, p1, p2, p3);

        buclePrincipal();
    }

    private Personaje determinarJugadorInicial(Personaje p1, Personaje p2, Personaje p3) {
        Personaje mayor = p1;
        if (p2.obtenerPoblacionMaxima() > mayor.obtenerPoblacionMaxima()) mayor = p2;
        if (p3.obtenerPoblacionMaxima() > mayor.obtenerPoblacionMaxima()) mayor = p3;
        return mayor;
    }

    private void configurarOrdenTurnos(Personaje primero, Personaje p1, Personaje p2, Personaje p3) {
        turnos.add(primero);
        if (p1 != primero) turnos.add(p1);
        if (p2 != primero) turnos.add(p2);
        if (p3 != primero) turnos.add(p3);
    }

    private void buclePrincipal() {
        while (enEjecucion) {
            Personaje jugadorActual = turnos.getActual();
            dinamicaTurno(jugadorActual);
            turnos.avanzar();
        }
    }

    public void dinamicaTurno(Personaje jugadorActual) {
        System.out.println("\n==========================================");
        System.out.println("TURNO DE: " + jugadorActual.getNombre());
        System.out.println("==========================================");

        // ------------------------------------------------
        // FASE 1: REALIZAR ACCIONES (Exactamente 4)[cite: 3]
        // ------------------------------------------------
        jugadorActual.executeAction(this);

        if (curasDescubiertas == 4) {
            System.out.println("\n¡VICTORIA! Se han descubierto las 4 curas[cite: 7].");
            enEjecucion = false;
            return;
        }

        // ------------------------------------------------
        // FASE 2: ROBAR 2 CARTAS DEL MAZO DE JUGADOR[cite: 6]
        // ------------------------------------------------
        for (int i = 0; i < 2; i++) {
            if (mazoJugadores.isEmpty()) {
                System.out.println("\n¡DERROTA! Se agotó el mazo de cartas de jugador[cite: 6].");
                enEjecucion = false;
                return;
            }

            Carta cartaRobada = mazoJugadores.pop();
            if (cartaRobada.esEpidemia()) {
                resolverEpidemia(cartaRobada); // Método reservado para tu compañero[cite: 6]
            } else {
                jugadorActual.agarrarCarta(cartaRobada);
            }
        }

        // ------------------------------------------------
        // FASE 3: INFECTAR CIUDADES[cite: 6]
        // ------------------------------------------------
        for (int i = 0; i < tasaInfeccion; i++) {
            if (mazoInfeccion.isEmpty()) break;

            CartaInfeccion cartaInf = (CartaInfeccion) mazoInfeccion.pop();
            Ciudad ciudadObjetivo = cartaInf.getCiudadReferenciada();

            if (ciudadObjetivo != null) {
                if (ciudadObjetivo.getCubos() < 3) {
                    if (cubosDisponibles <= 0) {
                        System.out.println("\n¡DERROTA! Se agotó la reserva global de cubos[cite: 6].");
                        enEjecucion = false;
                        return;
                    }
                    ciudadObjetivo.agregarCubo();
                    cubosDisponibles--;
                }
            }
        }
    }

    // Métodos de coordinación y actualización de estado
    public void cambiarUbicacion(Personaje p, Ciudad destino) {
        if (p.getCiudadActual() != null) {
            p.getCiudadActual().removerPersonaje(p);
        }
        p.setCiudadActual(destino);
        destino.agregarPersonaje(p);
    }

    public void registrarCuraDescubierta() {
        this.curasDescubiertas++;
    }

    private void resolverEpidemia(Carta cartaEpidemia) {
        // Reservado para la lógica de epidemias de tu compañero[cite: 6]
    }
}