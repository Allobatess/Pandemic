package juego.mazo;
import datosobjetos.infocartas;
import mundo.cartas.carta;
import mundo.cartas.cartaCiudad;
import java.util.Random;
//clase maazojugador, tiene dos metodos, crearMazoInicial y barajar, el primero crea un mazo de cartas de ciudad y el segundo baraja el mazo de cartas
 
public class mazoJugador {
    ///Arregloooooooooo//

    //Tenemos un arreglo de 53 cartas, 48 cartas de ciudad, 5 cartas de evento
    // 12 de estas cartas, 6 sera para repartir y las otras estaran en el top de la pila. 
    public static carta[] crearMazoInicial() {

        cartaCiudad[] cartasCiudad =
            infocartas.crearCartasCiudad();

        carta[] mazoinicial = new carta[53];
        for (int i = 0; i < cartasCiudad.length; i++) {
            mazoinicial[i] = cartasCiudad[i];
        }
        mazoinicial[48] = infocartas.pronostico;
        mazoinicial[49] = infocartas.puenteAereo;
        mazoinicial[50] = infocartas.nocheTranquila;
        mazoinicial[51] = infocartas.subvencionGobierno;
        mazoinicial[52] = infocartas.poblacionResistente;


        return mazoinicial;
    }
    //-----------------------------------------------------------------
    // Barajar el mazo de cartas
    public static void barajar(carta[] mazoinicial) {
        Random random = new Random();

        for (int i = mazoinicial.length - 1; i > 0; i--) {

            int j = random.nextInt(i + 1);

            carta temporal = mazoinicial[i];
            mazoinicial[i] = mazoinicial[j];
            mazoinicial[j] = temporal;
        }
    }
}

    

