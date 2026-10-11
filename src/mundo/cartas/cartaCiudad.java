package mundo.cartas;

//estas son las cartas con las que se encuentran la cura

import mundo.ciudad;

public class cartaCiudad extends carta {
    //extends carta, significa que cartaCiudad hereda de la clase carta, 
    // lo que significa que cartaCiudad es una subclase de carta y puede acceder a sus métodos y atributos.
    //Pos se supone que esta cosa se hace para no volver a escribir ciudades xd

    private final ciudad ciudad_asociada; //Atributo 
    //ciudad_asociada es un atributo privado de la clase cartaCiudad que almacena una referencia a un objeto de tipo ciudad.

    public cartaCiudad(ciudad ciudad_asociada) { //Constructor
//  acceso + constructor + parametros, para crear una nueva cartaCiudad, se necesitaun objeto de tipo ciudad que se pasa como argumento al constructor.

        super(ciudad_asociada.ObtenerNombre(), "Ciudad"); //constructor de la clase padre carta 
        //super, llama al constructor de la clase padre carta
        this.ciudad_asociada = ciudad_asociada;
        //atributo del objeto cartaCiudad = parametro que llego al constructor
    }

    public ciudad ObtenerCiudad() { //metodo
    //acceso + tipo de retorno + nombre del metodo, este metodo devuelve el objeto ciudad asociado a la cartaCiudad.
        return ciudad_asociada;
    }
}