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
}
