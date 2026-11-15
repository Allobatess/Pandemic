package mundo.roles;

import juego.LogicaJuego;
import mundo.Ciudad;
import mundo.Personaje;
import mundo.cartas.Carta;
import mundo.cartas.CartaCiudad;
import java.util.Scanner;

/**
 * Clase base que representa el rol de un jugador.
 * Define el menú estándar, las acciones básicas y los métodos hook para habilidades pasivas.
 */
public class Rol {

    protected final String nombre;
    protected final String descripcion;

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerDescripcion() {
        return descripcion;
    }

    // ==========================================
    // MÉTODOS HOOK PARA HABILIDADES Y REGLAS DE ROL
    // ==========================================

    public void alEntrarACiudad(Personaje personaje, Ciudad ciudad, LogicaJuego juego) {
        // Por defecto sin efecto pasivo al entrar
    }

    public void alDescubrirCura(Personaje personaje, LogicaJuego juego) {
        // Por defecto sin efecto pasivo al descubrir cura
    }

    public boolean protegeCiudad(Personaje personaje, Ciudad ciudadObjetivo, LogicaJuego juego) {
        return false;
    }

    public boolean puedeEntregarCualquierCartaCiudad() {
        return false;
    }

    // ==========================================
    // MENÚ ESTÁNDAR DE ACCIONES
    // ==========================================

    public int ejecutarMenu(Personaje personaje, LogicaJuego juego) {
        Scanner scanner = personaje.obtenerScanner();

        imprimirOpcionesBase();
        System.out.println("  0. Pasar / Finalizar acciones restantes");
        System.out.print("Opción: ");

        int opcion = leerEntero(scanner);
        if (opcion == Integer.MIN_VALUE) {
            System.out.println("Entrada inválida. Ingrese un número válido.");
            return 0;
        }

        return procesarOpcionBase(opcion, personaje, juego);
    }

    protected void imprimirOpcionesBase() {
        System.out.println("\nSeleccione una acción (" + nombre + "):");
        System.out.println("  1. Mover a ciudad vecina (normalMove)");
        System.out.println("  2. Vuelo directo (destinyDirectMove - descartar carta del destino)");
        System.out.println("  3. Vuelo chárter (anyDirectMove - descartar carta de ciudad actual)");
        System.out.println("  4. Tratar enfermedad (tratarEnfermedad)");
        System.out.println("  5. Construir centro de investigación (construirCentro)");
        System.out.println("  6. Mover entre centros de investigación (transCenterMove)");
        System.out.println("  7. Descubrir cura (descubrirCura)");
        System.out.println("  8. Compartir conocimiento (compartirConocimiento)");
    }

    protected int procesarOpcionBase(int opcion, Personaje personaje, LogicaJuego juego) {
        switch (opcion) {
            case 0:
                System.out.println("Has decidido finalizar tus acciones restantes.");
                return -1;
            case 1:
                return ejecutarMoverVecino(personaje, personaje, juego) ? 1 : 0;
            case 2:
                return ejecutarVueloDirecto(personaje, personaje, juego) ? 1 : 0;
            case 3:
                return ejecutarVueloCharter(personaje, personaje, juego) ? 1 : 0;
            case 4:
                return tratarEnfermedad(personaje) ? 1 : 0;
            case 5:
                return construirCentro(personaje, juego) ? 1 : 0;
            case 6:
                return ejecutarTransCenterMove(personaje, juego) ? 1 : 0;
            case 7:
                return ejecutarDescubrirCura(personaje, juego) ? 1 : 0;
            case 8:
                return ejecutarCompartirConocimiento(personaje, juego) ? 1 : 0;
            default:
                System.out.println("Opción fuera de rango.");
                return 0;
        }
    }

    // ==========================================
    // ACCIONES BÁSICAS (SOBRESCRIBIBLES)
    // ==========================================

    public boolean normalMove(Personaje peonAMover, Ciudad ciudadDestino, LogicaJuego juego) {
        if (peonAMover.obtenerCiudadActual().esVecino(ciudadDestino)) {
            juego.cambiarUbicacion(peonAMover, ciudadDestino);
            return true;
        }
        System.out.println("Movimiento inválido: La ciudad no es adyacente.");
        return false;
    }

    public boolean destinyDirectMove(Personaje pagadorCarta, Personaje peonAMover, int indiceCartaDestino, LogicaJuego juego) {
        Carta[] mano = pagadorCarta.obtenerMano();
        if (indiceCartaDestino >= 0 && indiceCartaDestino < pagadorCarta.obtenerNumCartas()) {
            Carta c = mano[indiceCartaDestino];
            if (c instanceof CartaCiudad) {
                CartaCiudad cCiudad = (CartaCiudad) c;
                Ciudad destino = cCiudad.obtenerCiudad();
                pagadorCarta.removerCartaPorIndice(indiceCartaDestino);
                juego.cambiarUbicacion(peonAMover, destino);
                return true;
            }
        }
        System.out.println("Carta inválida para vuelo directo.");
        return false;
    }

    public boolean anyDirectMove(Personaje pagadorCarta, Personaje peonAMover, int indiceCartaCiudadOrigen, Ciudad ciudadDestino, LogicaJuego juego) {
        Carta[] mano = pagadorCarta.obtenerMano();
        Ciudad ciudadOrigen = peonAMover.obtenerCiudadActual();

        if (indiceCartaCiudadOrigen >= 0 && indiceCartaCiudadOrigen < pagadorCarta.obtenerNumCartas()) {
            Carta c = mano[indiceCartaCiudadOrigen];
            if (c instanceof CartaCiudad) {
                CartaCiudad cCiudad = (CartaCiudad) c;
                if (cCiudad.obtenerNombre().equalsIgnoreCase(ciudadOrigen.obtenerNombre())) {
                    pagadorCarta.removerCartaPorIndice(indiceCartaCiudadOrigen);
                    juego.cambiarUbicacion(peonAMover, ciudadDestino);
                    return true;
                }
            }
        }
        System.out.println("Para vuelo chárter debes descartar la carta de " + ciudadOrigen.obtenerNombre() + ".");
        return false;
    }

    public boolean tratarEnfermedad(Personaje personaje) {
        Ciudad ciudadActual = personaje.obtenerCiudadActual();
        if (ciudadActual.obtenerCubosDeInfeccion() > 0) {
            ciudadActual.quitarCuboDeInfeccion();
            System.out.println("¡Éxito! Se removió 1 cubo en " + ciudadActual.obtenerNombre()
                    + ". Cubos restantes: " + ciudadActual.obtenerCubosDeInfeccion());
            return true;
        }
        System.out.println("No hay cubos de infección en " + ciudadActual.obtenerNombre() + ".");
        return false;
    }

    public boolean construirCentro(Personaje personaje, LogicaJuego juego) {
        Ciudad ciudadActual = personaje.obtenerCiudadActual();

        if (ciudadActual.tenerCentroDeInvestigacion()) {
            System.out.println("Esta ciudad ya cuenta con un centro de investigación.");
            return false;
        }

        int idxCartaActual = obtenerIndiceCartaDeCiudad(personaje, ciudadActual.obtenerNombre());
        if (idxCartaActual == -1) {
            System.out.println("Necesitas tener en tu mano la carta de "
                    + ciudadActual.obtenerNombre() + " para construir un centro de investigación.");
            return false;
        }

        if (juego.puedeConstruirCentro()) {
            personaje.removerCartaPorIndice(idxCartaActual);
            juego.registrarCentroDeInvestigacion(ciudadActual);
            System.out.println("¡Éxito! Se ha construido un centro de investigación en "
                    + ciudadActual.obtenerNombre() + " (" + juego.obtenerCentrosConstruidos() + "/6).");
            return true;
        } else {
            Ciudad origen = seleccionarCentroAReposicionar(personaje, juego);
            if (origen == null) {
                return false;
            }
            personaje.removerCartaPorIndice(idxCartaActual);
            juego.moverCentroDeInvestigacion(origen, ciudadActual);
            System.out.println("¡Éxito! Centro de investigación reposicionado de "
                    + origen.obtenerNombre() + " a " + ciudadActual.obtenerNombre() + " (6/6).");
            return true;
        }
    }

    protected Ciudad seleccionarCentroAReposicionar(Personaje personaje, LogicaJuego juego) {
        Ciudad[] centros = juego.obtenerCiudadesConCentro();
        System.out.println("Ya existen 6 centros de investigación en el mapa. Elige cuál deseas mover a "
                + personaje.obtenerCiudadActual().obtenerNombre() + ":");
        for (int i = 0; i < centros.length; i++) {
            System.out.println("  [" + i + "] " + centros[i].obtenerNombre());
        }
        System.out.print("Índice del centro a retirar (o -1 para cancelar): ");

        int idx = leerEntero(personaje.obtenerScanner());
        if (idx >= 0 && idx < centros.length) {
            return centros[idx];
        }
        System.out.println("Reposicionamiento cancelado.");
        return null;
    }

    public boolean transCenterMove(Personaje peonAMover, Ciudad ciudadDestino, LogicaJuego juego) {
        Ciudad ciudadActual = peonAMover.obtenerCiudadActual();

        if (!ciudadActual.tenerCentroDeInvestigacion()) {
            System.out.println("Debes estar en una ciudad con centro de investigación para usar este traslado.");
            return false;
        }

        if (ciudadDestino == null || !ciudadDestino.tenerCentroDeInvestigacion() || ciudadDestino == ciudadActual) {
            System.out.println("El destino debe ser otra ciudad con centro de investigación.");
            return false;
        }

        juego.cambiarUbicacion(peonAMover, ciudadDestino);
        return true;
    }

    public boolean descubrirCura(Personaje jugador, String color, LogicaJuego juego) {
        if (!jugador.obtenerCiudadActual().tenerCentroDeInvestigacion()) {
            System.out.println("Debes estar en una estación de investigación.");
            return false;
        }

        int indice = juego.indiceColor(color);
        if (indice == -1) {
            System.out.println("Color inválido. Elige Azul, Amarillo, Negro o Rojo.");
            return false;
        }

        if (juego.estaCurada(indice)) {
            System.out.println("Ya se descubrió la cura de ese color.");
            return false;
        }

        if (!jugador.descartarCartasDeCiudad(LogicaJuego.COLORES[indice], 5)) {
            System.out.println("Necesitas cinco cartas de ciudad del mismo color.");
            return false;
        }

        juego.registrarCura(indice);
        System.out.println("¡Cura descubierta! Color: " + LogicaJuego.COLORES[indice] + ".");
        return true;
    }

    public boolean compartirConocimiento(Personaje emisor, Personaje receptor, int indiceCarta) {
        if (emisor == null || receptor == null || emisor == receptor) {
            System.out.println("Jugadores inválidos para compartir conocimiento.");
            return false;
        }

        Ciudad ciudadComun = emisor.obtenerCiudadActual();
        if (ciudadComun != receptor.obtenerCiudadActual()) {
            System.out.println("Ambos jugadores deben estar en la misma ciudad.");
            return false;
        }

        if (indiceCarta < 0 || indiceCarta >= emisor.obtenerNumCartas()
                || !(emisor.obtenerMano()[indiceCarta] instanceof CartaCiudad)) {
            System.out.println("Elige una carta de ciudad válida.");
            return false;
        }

        CartaCiudad carta = (CartaCiudad) emisor.obtenerMano()[indiceCarta];

        if (!emisor.obtenerRol().puedeEntregarCualquierCartaCiudad() && carta.obtenerCiudad() != ciudadComun) {
            System.out.println("La carta debe ser de " + ciudadComun.obtenerNombre() + ".");
            return false;
        }

        emisor.removerCartaPorIndice(indiceCarta);
        receptor.agarrarCarta(carta);
        System.out.println(receptor.obtenerNombre() + " recibió la carta " + carta.obtenerNombre() + ".");
        return true;
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE INTERACCIÓN
    // ==========================================

    protected boolean ejecutarMoverVecino(Personaje controlador, Personaje peonAMover, LogicaJuego juego) {
        Scanner scanner = controlador.obtenerScanner();
        Ciudad[] vecinos = peonAMover.obtenerCiudadActual().obtenerVecinos();

        if (vecinos.length == 0) {
            System.out.println("Esta ciudad no tiene conexiones vecinas.");
            return false;
        }

        System.out.println("Ciudades vecinas disponibles desde " + peonAMover.obtenerCiudadActual().obtenerNombre() + ":");
        for (int i = 0; i < vecinos.length; i++) {
            System.out.println("  [" + i + "] " + vecinos[i].obtenerNombre());
        }
        System.out.print("Seleccione el índice del destino: ");

        int idx = leerEntero(scanner);
        if (idx >= 0 && idx < vecinos.length) {
            return normalMove(peonAMover, vecinos[idx], juego);
        }
        System.out.println("Índice de vecino inválido.");
        return false;
    }

    protected boolean ejecutarVueloDirecto(Personaje pagadorCarta, Personaje peonAMover, LogicaJuego juego) {
        Scanner scanner = pagadorCarta.obtenerScanner();
        if (pagadorCarta.obtenerNumCartas() == 0) {
            System.out.println("No tienes cartas en la mano.");
            return false;
        }

        System.out.print("Ingrese el índice de la Carta Ciudad a descartar para volar a ella: ");
        int idx = leerEntero(scanner);
        if (idx != Integer.MIN_VALUE) {
            return destinyDirectMove(pagadorCarta, peonAMover, idx, juego);
        }
        return false;
    }

    protected boolean ejecutarVueloCharter(Personaje pagadorCarta, Personaje peonAMover, LogicaJuego juego) {
        Scanner scanner = pagadorCarta.obtenerScanner();
        Ciudad ciudadOrigen = peonAMover.obtenerCiudadActual();
        int idxCartaOrigen = obtenerIndiceCartaDeCiudad(pagadorCarta, ciudadOrigen.obtenerNombre());

        if (idxCartaOrigen == -1) {
            System.out.println("No posees la Carta Ciudad de " + ciudadOrigen.obtenerNombre() + " para realizar vuelo chárter.");
            return false;
        }

        System.out.print("Ingrese el nombre exacto de la ciudad a la cual desea volar: ");
        String nombreDestino = scanner.nextLine().trim();
        Ciudad ciudadDestino = juego.buscarCiudad(nombreDestino);

        if (ciudadDestino != null) {
            return anyDirectMove(pagadorCarta, peonAMover, idxCartaOrigen, ciudadDestino, juego);
        } else {
            System.out.println("Ciudad no encontrada en el tablero.");
            return false;
        }
    }

    protected boolean ejecutarTransCenterMove(Personaje peonAMover, LogicaJuego juego) {
        Ciudad ciudadActual = peonAMover.obtenerCiudadActual();
        if (!ciudadActual.tenerCentroDeInvestigacion()) {
            System.out.println("No se encuentra en una ciudad con centro de investigación.");
            return false;
        }

        Ciudad[] tablero = juego.obtenerTableroCiudades();
        Ciudad[] centrosDisponibles = new Ciudad[tablero.length];
        int totalCentros = 0;

        for (Ciudad c : tablero) {
            if (c.tenerCentroDeInvestigacion() && c != ciudadActual) {
                centrosDisponibles[totalCentros++] = c;
            }
        }

        if (totalCentros == 0) {
            System.out.println("No hay otros centros de investigación construidos en el mapa.");
            return false;
        }

        System.out.println("Centros de investigación disponibles:");
        for (int i = 0; i < totalCentros; i++) {
            System.out.println("  [" + i + "] " + centrosDisponibles[i].obtenerNombre());
        }
        System.out.print("Seleccione el índice del destino: ");

        Scanner scanner = peonAMover.obtenerScanner();
        int idx = leerEntero(scanner);
        if (idx >= 0 && idx < totalCentros) {
            return transCenterMove(peonAMover, centrosDisponibles[idx], juego);
        }
        System.out.println("Índice inválido.");
        return false;
    }

    protected boolean ejecutarDescubrirCura(Personaje personaje, LogicaJuego juego) {
        Scanner scanner = personaje.obtenerScanner();
        System.out.print("Ingrese el color de la enfermedad a curar (Azul, Amarillo, Negro, Rojo): ");
        String color = scanner.nextLine().trim();
        return descubrirCura(personaje, color, juego);
    }

    protected boolean ejecutarCompartirConocimiento(Personaje personaje, LogicaJuego juego) {
        Scanner scanner = personaje.obtenerScanner();
        Ciudad ciudadActual = personaje.obtenerCiudadActual();
        Personaje[] jugadores = juego.obtenerJugadores();
        boolean hayCompaneros = false;

        System.out.println("Jugadores en " + ciudadActual.obtenerNombre() + ":");
        for (int i = 0; i < jugadores.length; i++) {
            Personaje otro = jugadores[i];
            if (otro != personaje && otro.obtenerCiudadActual() == ciudadActual) {
                System.out.println("  [" + i + "] " + otro.obtenerNombre());
                hayCompaneros = true;
            }
        }

        if (!hayCompaneros) {
            System.out.println("No hay otro jugador en esta ciudad.");
            return false;
        }

        System.out.print("Elige el índice del jugador: ");
        int idxJugador = leerEntero(scanner);
        if (idxJugador < 0 || idxJugador >= jugadores.length) {
            System.out.println("Jugador inválido.");
            return false;
        }

        Personaje otro = jugadores[idxJugador];
        if (otro == personaje || otro.obtenerCiudadActual() != ciudadActual) {
            System.out.println("Elige otro jugador que esté en tu ciudad.");
            return false;
        }

        System.out.print("1. Dar una carta | 2. Recibir una carta: ");
        int modo = leerEntero(scanner);
        if (modo != 1 && modo != 2) {
            System.out.println("Opción inválida.");
            return false;
        }

        Personaje emisor = (modo == 1) ? personaje : otro;
        Personaje receptor = (modo == 1) ? otro : personaje;

        if (emisor.obtenerNumCartas() == 0) {
            System.out.println(emisor.obtenerNombre() + " no tiene cartas en su mano.");
            return false;
        }

        emisor.imprimirMano();
        System.out.print("Índice de la carta de ciudad: ");
        int indiceCarta = leerEntero(scanner);

        if (indiceCarta < 0 || indiceCarta >= emisor.obtenerNumCartas()
                || !(emisor.obtenerMano()[indiceCarta] instanceof CartaCiudad)) {
            System.out.println("Elige una carta de ciudad válida.");
            return false;
        }

        CartaCiudad carta = (CartaCiudad) emisor.obtenerMano()[indiceCarta];

        if (!emisor.obtenerRol().puedeEntregarCualquierCartaCiudad() && carta.obtenerCiudad() != ciudadActual) {
            System.out.println("La carta debe ser de " + ciudadActual.obtenerNombre() + ".");
            return false;
        }

        System.out.print(otro.obtenerNombre() + ", ¿aceptas transferir la carta "
                + carta.obtenerNombre() + "? (s/n): ");
        if (!scanner.hasNextLine() || !scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Intercambio cancelado.");
            return false;
        }

        return compartirConocimiento(emisor, receptor, indiceCarta);
    }

    protected int obtenerIndiceCartaDeCiudad(Personaje personaje, String nombreCiudad) {
        Carta[] mano = personaje.obtenerMano();
        for (int i = 0; i < personaje.obtenerNumCartas(); i++) {
            if (mano[i] instanceof CartaCiudad) {
                if (mano[i].obtenerNombre().equalsIgnoreCase(nombreCiudad)) {
                    return i;
                }
            }
        }
        return -1;
    }

    protected int leerEntero(Scanner scanner) {
        if (scanner.hasNextInt()) {
            int val = scanner.nextInt();
            scanner.nextLine();
            return val;
        } else {
            scanner.nextLine();
            return Integer.MIN_VALUE;
        }
    }
}
