package mundo;

public class ciudad {

    private String nombre;
    private String color;
    private int cubos_de_infeccion;
    private boolean centro_de_investigacion;

    public ciudad(String nombre, String color) {
        this.nombre = nombre;
        this.color = color; 
        this.cubos_de_infeccion = 0;
        this.centro_de_investigacion= false;
    }

    public String ObtenerNombre() {    // Método para obtener el nombre de la ciudad
        return nombre;
    }

    public String ObtenerColor() { // Método para obtener el color de la ciudad 
        return color;
    }

    public int Obtener_CubosDeInfeccion() { // Método para obtener la cantidad de cubos de infección en la ciudad
        return cubos_de_infeccion;
    }

    public boolean Tener_CentroDeInvestigacion() { // Método para verificar si la ciudad tiene un centro de investigación   
        return centro_de_investigacion;
    }

    public void Agregar_CuboDeInfeccion() { // Método para agregar un cubo de infección a la ciudad
        cubos_de_infeccion++;
    }

    public void Quitar_CuboDeInfeccion() { // Método para quitar un cubo de infección de la ciudad
        if (cubos_de_infeccion > 0) {
            cubos_de_infeccion--;
        }
    }

    public void Construir_CentroDeInvestigacion() { // Método para construir un centro de investigación en la ciudad
        centro_de_investigacion = true;
    }

    public void printCity() {
        System.out.println("Ciudad: " + nombre);
        System.out.println("Color: " + color);
        System.out.println("Cubos de infección: " + cubos_de_infeccion);
        System.out.println("Centro de investigación: " + centro_de_investigacion);
    }
}