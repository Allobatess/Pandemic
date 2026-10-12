import datosobjetos.infocartas;
import mundo.cartas.carta;
import mundo.cartas.cartaCiudad;
import mundo.cartas.cartaEpidemia;
import juego.mazo.mazoJugador;

public class Main {

    public static void main(String[] args) {
        //evento
        System.out.println(infocartas.pronostico.ObtenerNombre());
        System.out.println(infocartas.pronostico.ObtenerTipo());
        System.out.println(infocartas.pronostico.ObtenerDescripcion());

        cartaCiudad[] cartas =
            infocartas.crearCartasCiudad();
        //ciudad
        System.out.println(cartas[0].ObtenerNombre());
        System.out.println(cartas[0].ObtenerTipo());

        System.out.println(cartas[1].ObtenerNombre());
        System.out.println(cartas[1].ObtenerTipo());

        cartaEpidemia[] epidemias =
            infocartas.crearCartasEpidemia(4);

        System.out.println("Cantidad de epidemias: " + epidemias.length);

        for (int i = 0; i < epidemias.length; i++) {

            System.out.println("Carta " + (i + 1));
            System.out.println("Nombre: " + epidemias[i].ObtenerNombre());
            System.out.println("Tipo: " + epidemias[i].ObtenerTipo());
            System.out.println("Descripcion: " + epidemias[i].ObtenerDescripcion());

            System.out.println();
        }


        carta[] mazo = mazoJugador.crearMazoInicial();
        mazoJugador.barajar(mazo);

        for (int i = 0; i < mazo.length; i++) {

            System.out.println(
                i + " - " +
                mazo[i].ObtenerNombre() +
                " | " +
                mazo[i].ObtenerTipo()
            );
        }
    }
}