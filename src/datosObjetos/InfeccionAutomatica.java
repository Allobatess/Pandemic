package datosObjetos;

import mundo.Ciudad;
import mundo.cartas.CartaInfeccion;

/**
 * Modulo que ejecuta la fase de infección automática al final de cada turno.
 * Extrae cartas del mazo de infección y agrega un cubo a cada ciudad revelada (máximo 3 cubos).
 */
public class InfeccionAutomatica {

    private final MazoInfeccion mazoInfeccion;

    public InfeccionAutomatica(MazoInfeccion mazoInfeccion) {
        this.mazoInfeccion = mazoInfeccion;
    }

    public void infectar(int cantidadDeCartas) {
        System.out.println("\n--- Fase de Infección Automática (" + cantidadDeCartas + " cartas) ---");
        for (int i = 0; i < cantidadDeCartas; i++) {
            CartaInfeccion robada = mazoInfeccion.robarCarta();
            if (robada != null) {
                Ciudad c = robada.obtenerCiudad();
                c.agregarCuboDeInfeccion();
                System.out.println("Infección en: " + c.obtenerNombre() + " | Cubos actuales: " + c.obtenerCubosDeInfeccion());
            } else {
                System.out.println("Mazo de infección agotado.");
            }
        }
    }
}