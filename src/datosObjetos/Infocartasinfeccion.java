mport mundo.ciudad;
import mundo.cartas.cartaInfeccion;

public class InfoCartasInfeccion {

    public static cartaInfeccion[] crearCartas(ciudad[] ciudades) {
        if (ciudades == null || ciudades.length != 48) {
            throw new IllegalArgumentException(
                    "Se necesita un arreglo con 48 ciudades.");
        }

        cartaInfeccion[] cartas = new cartaInfeccion[ciudades.length];

        for (int i = 0; i < ciudades.length; i++) {
            cartas[i] = new cartaInfeccion(ciudades[i]);
        }

        return cartas;
    }
}
