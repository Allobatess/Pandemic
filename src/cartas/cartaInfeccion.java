package cartas;

import mundo.ciudad;

public class cartaInfeccion extends carta {

    private ciudad ciudad_asociada;

    public cartaInfeccion(ciudad ciudad_asociada) {
        super(ciudad_asociada.ObtenerNombre(), "Infeccion");

        this.ciudad_asociada = ciudad_asociada;
    }

    public ciudad ObtenerCiudad() {
        return ciudad_asociada;
    }
}