package mundo;

import juego.LogicaJuego;
import mundo.cartas.Carta;
import mundo.cartas.CartaCiudad;
import mundo.roles.Rol;
import java.util.Scanner;

/**
 * Representa al jugador en la partida. Administra su rol, posición, mano (FixedArray de 8)
 * y el control de sus 4 acciones por turno.
 */
public class Personaje {

    private final Rol rol;
    private Ciudad ciudadActual;
    private final Carta[] mano;
    private int numCartas;
    private int accionesDisponibles;
    private final Scanner scanner;

    public Personaje(Rol rol, Ciudad ciudadInicial) {
        this.rol = rol;
        this.ciudadActual = ciudadInicial;
        this.mano = new Carta[8]; // FixedArray de capacidad 8
        this.numCartas = 0;
        this.accionesDisponibles = 0;
        this.scanner = new Scanner(System.in);
    }

    public String obtenerNombre() {
        return rol.obtenerNombre();
    }

    public Rol obtenerRol() {
        return rol;
    }

    public Scanner obtenerScanner() {
        return scanner;
    }

    public Ciudad obtenerCiudadActual() {
        return ciudadActual;
    }

    public void establecerCiudadActual(Ciudad ciudadActual) {
        this.ciudadActual = ciudadActual;
    }

    public Carta[] obtenerMano() {
        return mano;
    }

    public int obtenerNumCartas() {
        return numCartas;
    }

    // ==========================================
    // GESTIÓN DE LA MANO
    // ==========================================

    public void agarrarCarta(Carta nuevaCarta) {
        if (nuevaCarta == null) return;
        mano[numCartas] = nuevaCarta;
        numCartas++;

        while (numCartas > 7) {
            descartarCarta();
        }
    }

    public void descartarCarta() {
        System.out.println("\n------------------------------------------");
        System.out.println("¡Mano llena para " + obtenerNombre() + "! (Límite: 7 cartas, tienes " + numCartas + ")");
        imprimirMano();

        int indiceAEliminar = -1;
        while (indiceAEliminar < 0 || indiceAEliminar >= numCartas) {
            System.out.print("Ingrese el índice de la carta que desea descartar (0 a " + (numCartas - 1) + "): ");
            if (scanner.hasNextInt()) {
                indiceAEliminar = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
            }
        }

        System.out.println("Descartaste: " + mano[indiceAEliminar].obtenerNombre());
        removerCartaPorIndice(indiceAEliminar);
    }

    public void removerCartaPorIndice(int indice) {
        if (indice >= 0 && indice < numCartas) {
            for (int i = indice; i < numCartas - 1; i++) {
                mano[i] = mano[i + 1];
            }
            mano[numCartas - 1] = null;
            numCartas--;
        }
    }

    public boolean descartarCartasDeCiudad(String color, int cantidad) {
        int contador = 0;
        for (int i = 0; i < numCartas; i++) {
            if (mano[i] instanceof CartaCiudad) {
                CartaCiudad cc = (CartaCiudad) mano[i];
                if (cc.obtenerCiudad().obtenerColor().equalsIgnoreCase(color)) {
                    contador++;
                }
            }
        }

        if (contador < cantidad) {
            return false;
        }

        int eliminadas = 0;
        for (int i = numCartas - 1; i >= 0 && eliminadas < cantidad; i--) {
            if (mano[i] instanceof CartaCiudad) {
                CartaCiudad cc = (CartaCiudad) mano[i];
                if (cc.obtenerCiudad().obtenerColor().equalsIgnoreCase(color)) {
                    System.out.println("Carta descartada para cura: " + mano[i].obtenerNombre());
                    removerCartaPorIndice(i);
                    eliminadas++;
                }
            }
        }
        return true;
    }

    public void imprimirMano() {
        System.out.println("Mano actual de " + obtenerNombre() + " (" + numCartas + "/7):");
        for (int i = 0; i < numCartas; i++) {
            System.out.println("  [" + i + "] " + mano[i].obtenerNombre() + " (" + mano[i].obtenerTipo() + ")");
        }
    }

    // ==========================================
    // EJECUCIÓN INTERACTIVA DEL TURNO
    // ==========================================

    public void ejecutarAccion(LogicaJuego juego) {
        this.accionesDisponibles = 4;

        while (accionesDisponibles > 0) {
            System.out.println("\n------------------------------------------");
            System.out.println("Ubicación actual: " + ciudadActual.obtenerNombre()
                    + " (Cubos de infección: " + ciudadActual.obtenerCubosDeInfeccion() + ")");
            System.out.println("Acciones restantes en este turno: " + accionesDisponibles);
            imprimirMano();

            int resultado = rol.ejecutarMenu(this, juego);
            if (juego.hayVictoria()) {
                return;
            }
            if (resultado == -1) {
                accionesDisponibles = 0;
                return;
            } else if (resultado == 1) {
                accionesDisponibles--;
            }
        }
    }
}