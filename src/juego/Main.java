package juego;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("██████   █████  ███╗  ██╗██████  ███████╗███╗   ███╗██╗ ██████");
        System.out.println("██   ██ ██   ██ ████╗ ██║██   ██ ██      ████╗ ████║██║██     ");
        System.out.println("██████  ███████ ██╔██╗██║██   ██ █████   ██╔████╔██║██║██     ");
        System.out.println("██      ██   ██ ██║╚████║██   ██ ██      ██║╚██╔╝██║██║██     ");
        System.out.println("██      ██   ██ ██║ ╚███║██████  ███████╗██║ ╚═╝ ██║██║ ██████");
        System.out.println();

        int cantidadJugadores = 0;
        while (cantidadJugadores < 2 || cantidadJugadores > 7) {
            System.out.print("Ingrese la cantidad de jugadores para la partida (2 a 7): ");
            if (scanner.hasNextInt()) {
                cantidadJugadores = scanner.nextInt();
                scanner.nextLine();
                if (cantidadJugadores < 2 || cantidadJugadores > 7) {
                    System.out.println("Número fuera de rango. Debe ser entre 2 y 7.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }

        LogicaJuego juego = new LogicaJuego(cantidadJugadores);
        juego.imprimirEstadoMapa();

        boolean juegoActivo = true;
        int numeroTurno = 1;

        while (juegoActivo) {
            System.out.println("\n******************************************");
            System.out.println("          INICIO DEL TURNO #" + numeroTurno);
            System.out.println("******************************************");

            boolean juegoContinua = juego.dinamicaTurno();

            if (!juegoContinua) {
                juegoActivo = false;
                if (!juego.hayVictoria()) {
                    System.out.println("\nFin de la partida: Los jugadores han sido derrotados.");
                }
                break;
            }

            System.out.print("\n¿Desean continuar al siguiente turno? (s/n): ");
            String respuesta = scanner.nextLine().trim();
            if (respuesta.equalsIgnoreCase("n")) {
                juegoActivo = false;
                System.out.println("Partida pausada/finalizada por decisión de los jugadores.");
            }

            numeroTurno++;
        }

        scanner.close();
    }
}