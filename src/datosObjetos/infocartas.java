package datos;
import mundo.ciudad;
import mundo.cartas.cartaCiudad;
import mundo.cartas.cartaEvento;


public class infocartas {
    // CARTAS DE EVENTO
    public static cartaEvento pronostico = new cartaEvento(
        "Pronostico",
        "Mira las 6 primeras cartas del mazo de infeccion y ordenalas como quieras."
    );

    public static cartaEvento puenteAereo = new cartaEvento(
        "Puente Aereo",
        "Mueve cualquier peon a cualquier ciudad."
    );

    public static cartaEvento nocheTranquila = new cartaEvento(
        "Una Noche Tranquila",
        "Omite la siguiente fase de infeccion."
    );

    public static cartaEvento subvencionGobierno = new cartaEvento(
        "Subvencion del Gobierno",
        "Construye una estacion de investigacion sin descartar una carta de ciudad."
    );

    public static cartaEvento poblacionResistente = new cartaEvento(
        "Poblacion Resistente",
        "Retira del descarte de infeccion una carta de ciudad."
    );
    //Cartas ciudad
    public static cartaCiudad[] crearCartasCiudad() {

        ciudad[] listaCiudades = ciudades.crearCiudades();

        cartaCiudad[] cartas = new cartaCiudad[listaCiudades.length];

        for (int i = 0; i < listaCiudades.length; i++) {
            cartas[i] = new cartaCiudad(listaCiudades[i]);
        }

        return cartas;
    }
    
}
