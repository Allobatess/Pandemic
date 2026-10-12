//
package datosobjetos;

import mundo.ciudad;

public class ciudades {

    public static ciudad[] crearCiudades() {

        ciudad[] ciudades = new ciudad[48];
        //“Crea un arreglo llamado ciudades que puede guardar 48 objetos de tipo ciudad"

        // Azul
        ciudades[0] = new ciudad("San Francisco", "Azul");
        ciudades[1] = new ciudad("Chicago", "Azul");
        ciudades[2] = new ciudad("Montreal", "Azul");
        ciudades[3] = new ciudad("Nueva York", "Azul");
        ciudades[4] = new ciudad("Washington", "Azul");
        ciudades[5] = new ciudad("Atlanta", "Azul");
        ciudades[6] = new ciudad("Londres", "Azul");
        ciudades[7] = new ciudad("Madrid", "Azul");
        ciudades[8] = new ciudad("Paris", "Azul");
        ciudades[9] = new ciudad("Essen", "Azul");
        ciudades[10] = new ciudad("Milan", "Azul");
        ciudades[11] = new ciudad("San Petersburgo", "Azul");

        // Amarillo
        ciudades[12] = new ciudad("Los Angeles", "Amarillo");
        ciudades[13] = new ciudad("Ciudad de Mexico", "Amarillo");
        ciudades[14] = new ciudad("Miami", "Amarillo");
        ciudades[15] = new ciudad("Bogota", "Amarillo");
        ciudades[16] = new ciudad("Lima", "Amarillo");
        ciudades[17] = new ciudad("Santiago", "Amarillo");
        ciudades[18] = new ciudad("Buenos Aires", "Amarillo");
        ciudades[19] = new ciudad("Sao Paulo", "Amarillo");
        ciudades[20] = new ciudad("Lagos", "Amarillo");
        ciudades[21] = new ciudad("Jartum", "Amarillo");
        ciudades[22] = new ciudad("Kinshasa", "Amarillo");
        ciudades[23] = new ciudad("Johannesburgo", "Amarillo");

        // Negro
        ciudades[24] = new ciudad("Argel", "Negro");
        ciudades[25] = new ciudad("El Cairo", "Negro");
        ciudades[26] = new ciudad("Estambul", "Negro");
        ciudades[27] = new ciudad("Moscu", "Negro");
        ciudades[28] = new ciudad("Bagdad", "Negro");
        ciudades[29] = new ciudad("Riad", "Negro");
        ciudades[30] = new ciudad("Teheran", "Negro");
        ciudades[31] = new ciudad("Karachi", "Negro");
        ciudades[32] = new ciudad("Mumbai", "Negro");
        ciudades[33] = new ciudad("Delhi", "Negro");
        ciudades[34] = new ciudad("Chennai", "Negro");
        ciudades[35] = new ciudad("Calcuta", "Negro");

        // Rojo
        ciudades[36] = new ciudad("Pekin", "Rojo");
        ciudades[37] = new ciudad("Seul", "Rojo");
        ciudades[38] = new ciudad("Tokio", "Rojo");
        ciudades[39] = new ciudad("Shanghai", "Rojo");
        ciudades[40] = new ciudad("Hong Kong", "Rojo");
        ciudades[41] = new ciudad("Taipei", "Rojo");
        ciudades[42] = new ciudad("Osaka", "Rojo");
        ciudades[43] = new ciudad("Bangkok", "Rojo");
        ciudades[44] = new ciudad("Ho Chi Minh", "Rojo");
        ciudades[45] = new ciudad("Manila", "Rojo");
        ciudades[46] = new ciudad("Yakarta", "Rojo");
        ciudades[47] = new ciudad("Sidney", "Rojo");

        return ciudades;
    }
}