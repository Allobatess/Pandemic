package juego;

import datosObjetos.Mazoinfeccion;
import mundo.cartas.cartaInfeccion;

public class InfeccionAutomatica {

    private final Mazoinfeccion mazoInfeccion;

    public InfeccionAutomatica(Mazoinfeccion mazoInfeccion) {
        this.mazoInfeccion = mazoInfeccion;
    }

    public void infectar(int cantidadDeCartas) {
        if (cantidadDeCartas < 0
                || cantidadDeCartas > mazoInfeccion.cartasRestantes()) {
            throw new IllegalArgumentException(
                    "La cantidad no es válida o no quedan suficientes cartas.");
        }

        for (int i = 0; i < cantidadDeCartas; i++) {
            cartaInfeccion carta = mazoInfeccion.robarCarta();
            carta.ObtenerCiudad().Agregar_CuboDeInfeccion();
        }
    }
}
