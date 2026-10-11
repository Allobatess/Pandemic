import datos.ciudades;
import mundo.ciudad;

public class Main {

    public static void main(String[] args) {

        ciudad[] mapa = ciudades.crearCiudades();

        mapa[0].printCity();
        mapa[1].printCity();
        mapa[2].printCity();
    }
}