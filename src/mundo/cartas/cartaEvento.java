package mundo.cartas;

public class cartaEvento extends carta {

    private String descripcion;
    

    public cartaEvento(String nombre, String descripcion) {
        super(nombre, "Evento");
        this.descripcion = descripcion;
    }

    public String ObtenerDescripcion() {
        return descripcion;
    }
}