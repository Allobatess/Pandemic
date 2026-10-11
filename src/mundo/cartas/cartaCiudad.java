//estas son las cartas con las que se encuentran la cura
package cartas;

import mundo.ciudad;

public class cartaCiudad extends carta {
    //extends carta, significa que cartaCiudad hereda de la clase carta, 
    // lo que significa que cartaCiudad es una subclase de carta y puede acceder a sus métodos y atributos.
    //Pos se supone que esta cosa se hace para no volver a escribir ciudades xd

    private ciudad ciudad_asociada; //Atributo 
    //ciudad_asociada es un atributo privado de la clase cartaCiudad que almacena una referencia a un objeto de tipo ciudad.

    public cartaCiudad(ciudad ciudad_asociada) { //Constructor
//  acceso + constructor + parametros, para crear una nueva cartaCiudad, se necesitaun objeto de tipo ciudad que se pasa como argumento al constructor.

        super(ciudad_asociada.ObtenerNombre(), "Ciudad"); //constructor de la clase padre carta 
        
        this.ciudad_asociada = ciudad_asociada;
    }

    public ciudad ObtenerCiudad() {
        return ciudad_asociada;
    }
}