package mundo.cartas;

public class carta {

    private final String nombre;
    private final String tipo;

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