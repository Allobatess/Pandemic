package datosobjetos;
import mundo.cartas.cartaCiudad;
import mundo.cartas.cartaEvento;
import mundo.cartas.cartaEpidemia;

import mundo.ciudad;


public class infocartas {
    // CARTAS DE EVENTO

    public static final cartaEvento pronostico = new cartaEvento(
        "Pronostico",
        "Mira las 6 primeras cartas del mazo de infeccion y ordenalas como quieras."
    );

    public static final cartaEvento puenteAereo = new cartaEvento(
        "Puente Aereo",
        "Mueve cualquier peon a cualquier ciudad."
    );

    public static final cartaEvento nocheTranquila = new cartaEvento(
        "Una Noche Tranquila",
        "Omite la siguiente fase de infeccion."
    );

    public static final cartaEvento subvencionGobierno = new cartaEvento(
        "Subvencion del Gobierno",
        "Construye una estacion de investigacion sin descartar una carta de ciudad."
    );

    public static final cartaEvento poblacionResistente = new cartaEvento(
        "Poblacion Resistente",
        "Retira una carta del descarte del mazo de infeccion."
    );
    // CARTAS DE CIUDAD

    public static cartaCiudad[] crearCartasCiudad() {

        ciudad[] listaCiudades = ciudades.crearCiudades();

        cartaCiudad[] cartasCiudad =
            new cartaCiudad[listaCiudades.length];

        for (int i = 0; i < listaCiudades.length; i++) {

            cartasCiudad[i] =
                new cartaCiudad(listaCiudades[i]);
        }

        return cartasCiudad;
    }
    //Carta epidemia 

    public static cartaEpidemia[] crearCartasEpidemia(int cantidad) {

        cartaEpidemia[] epidemias = new cartaEpidemia[cantidad];

        for (int i = 0; i < cantidad; i++) {
            epidemias[i] = new cartaEpidemia("Aumenta el nivel de infeccion, infecta con 3 cubos una ciudad del mazo de infeccion e intensifica el mazo.");
    }
    return epidemias;
}

    
}
