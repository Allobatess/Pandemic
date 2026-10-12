package datosObjetos;

import java.util.Random;
import mundo.ciudad;
import mundo.cartas.cartaInfeccion;

public class Mazoinfeccion {

    private final cartaInfeccion[] cartas;
    private int siguienteCarta = 0;

    public Mazoinfeccion(ciudad[] ciudades) {
        cartas = InfoCartasInfeccion.crearCartas(ciudades);
        mezclar();
    }

    private void mezclar() {
        Random aleatorio = new Random();

        for (int i = cartas.length - 1; i > 0; i--) {
            int posicion = aleatorio.nextInt(i + 1);

            cartaInfeccion temporal = cartas[i];
            cartas[i] = cartas[posicion];
            cartas[posicion] = temporal;
        }
    }

    public cartaInfeccion robarCarta() {
        if (siguienteCarta >= cartas.length) {
            throw new IllegalStateException("No quedan cartas de infección.");
        }

        return cartas[siguienteCarta++];
    }

    public int cartasRestantes() {
        return cartas.length - siguienteCarta;
    }

    public void prepararInfeccionInicial() {
        if (siguienteCarta != 0) {
            throw new IllegalStateException(
                    "La infección inicial solo se puede preparar una vez.");
        }

        for (int i = 0; i < 9; i++) {
            cartaInfeccion carta = robarCarta();
            ciudad ciudadInfectada = carta.ObtenerCiudad();

            int cantidadCubos;
            if (i < 3) {
                cantidadCubos = 3;
            } else if (i < 6) {
                cantidadCubos = 2;
            } else {
                cantidadCubos = 1;
            }

            for (int j = 0; j < cantidadCubos; j++) {
                ciudadInfectada.Agregar_CuboDeInfeccion();
            }
        }
    }

    public void infectarAutomaticamente(int cantidadDeCartas) {
        if (cantidadDeCartas < 0 || cantidadDeCartas > cartasRestantes()) {
            throw new IllegalArgumentException(
                    "La cantidad no es válida o no quedan suficientes cartas.");
        }

        for (int i = 0; i < cantidadDeCartas; i++) {
            cartaInfeccion carta = robarCarta();
            carta.ObtenerCiudad().Agregar_CuboDeInfeccion();
        }
    }
}
