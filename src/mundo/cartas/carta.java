package mundo.cartas;

public class carta {

    private String nombre;
    private String tipo;

    public carta(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String ObtenerNombre() {
        return nombre;
    }

    public String ObtenerTipo() {
        return tipo;
    }
}