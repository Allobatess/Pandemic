package mundo.roles;

import juego.LogicaJuego;
import mundo.Ciudad;
import mundo.Personaje;
import java.util.Scanner;

public class Despachador extends Rol {

    public Despachador() {
        super(
            "Despachador",
            "Como acción, puede mover el peón de otro jugador (con su permiso) a una ciudad con otro peón, o moverlo como si fuera suyo descartando cartas de su propia mano para vuelos."
        );
    }

    @Override
    public int ejecutarMenu(Personaje personaje, LogicaJuego juego) {
        Scanner scanner = personaje.obtenerScanner();

        imprimirOpcionesBase();
        System.out.println("  9. [Habilidad] Mover cualquier peón a la ciudad de otro peón (pawnDirectMove)");
        System.out.println("  10. [Habilidad] Mover cualquier peón con reglas de movimiento normal/cartas propias (mimicTurnMove)");
        System.out.println("  0. Pasar / Finalizar acciones restantes");
        System.out.print("Opción: ");

        int opcion = leerEntero(scanner);
        if (opcion == Integer.MIN_VALUE) {
            System.out.println("Entrada inválida. Ingrese un número válido.");
            return 0;
        }

        switch (opcion) {
            case 9:
                return ejecutarPawnDirectMove(personaje, juego) ? 1 : 0;
            case 10:
                return mimicTurnMove(personaje, juego) ? 1 : 0;
            default:
                return procesarOpcionBase(opcion, personaje, juego);
        }
    }

    public boolean pawnDirectMove(Personaje personajeAMover, Personaje personajeDestino, LogicaJuego juego) {
        if (personajeAMover == null || personajeDestino == null) {
            System.out.println("Personajes inválidos.");
            return false;
        }

        Ciudad ciudadDestino = personajeDestino.obtenerCiudadActual();
        if (personajeAMover.obtenerCiudadActual() == ciudadDestino) {
            System.out.println(personajeAMover.obtenerNombre() + " ya se encuentra en " + ciudadDestino.obtenerNombre() + ".");
            return false;
        }

        juego.cambiarUbicacion(personajeAMover, ciudadDestino);
        return true;
    }

    private boolean ejecutarPawnDirectMove(Personaje despachador, LogicaJuego juego) {
        Scanner scanner = despachador.obtenerScanner();
        Personaje[] jugadores = juego.obtenerJugadores();

        System.out.println("\nJugadores en partida:");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("  [" + i + "] " + jugadores[i].obtenerNombre()
                    + " -> " + jugadores[i].obtenerCiudadActual().obtenerNombre());
        }

        System.out.print("Seleccione el índice del personaje que desea mover: ");
        int idxMover = leerEntero(scanner);
        if (idxMover < 0 || idxMover >= jugadores.length) {
            System.out.println("Índice de personaje inválido.");
            return false;
        }

        System.out.print("Seleccione el índice del personaje cuya ubicación será el destino: ");
        int idxDestino = leerEntero(scanner);
        if (idxDestino < 0 || idxDestino >= jugadores.length) {
            System.out.println("Índice de destino inválido.");
            return false;
        }

        return pawnDirectMove(jugadores[idxMover], jugadores[idxDestino], juego);
    }

    public boolean mimicTurnMove(Personaje despachador, LogicaJuego juego) {
        Scanner scanner = despachador.obtenerScanner();
        Personaje[] jugadores = juego.obtenerJugadores();

        System.out.println("\nSeleccione el peón que desea mover:");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("  [" + i + "] " + jugadores[i].obtenerNombre()
                    + " (en " + jugadores[i].obtenerCiudadActual().obtenerNombre() + ")");
        }
        System.out.print("Índice del peón: ");
        int idxPeon = leerEntero(scanner);

        if (idxPeon < 0 || idxPeon >= jugadores.length) {
            System.out.println("Índice de personaje inválido.");
            return false;
        }

        Personaje peonObjetivo = jugadores[idxPeon];
        System.out.println("\nTipo de movimiento para " + peonObjetivo.obtenerNombre()
                + " (desde " + peonObjetivo.obtenerCiudadActual().obtenerNombre() + "):");
        System.out.println("  1. Mover a ciudad vecina (normalMove)");
        System.out.println("  2. Vuelo directo usando carta del Despachador (destinyDirectMove)");
        System.out.println("  3. Vuelo chárter usando carta de " + peonObjetivo.obtenerCiudadActual().obtenerNombre() + " del Despachador (anyDirectMove)");
        System.out.println("  4. Mover entre centros de investigación (transCenterMove)");
        System.out.println("  0. Cancelar");
        System.out.print("Opción de movimiento: ");

        int tipoMov = leerEntero(scanner);
        switch (tipoMov) {
            case 1:
                return ejecutarMoverVecino(despachador, peonObjetivo, juego);
            case 2:
                return ejecutarVueloDirecto(despachador, peonObjetivo, juego);
            case 3:
                return ejecutarVueloCharter(despachador, peonObjetivo, juego);
            case 4:
                return ejecutarTransCenterMove(peonObjetivo, juego);
            default:
                System.out.println("Movimiento cancelado.");
                return false;
        }
    }
}