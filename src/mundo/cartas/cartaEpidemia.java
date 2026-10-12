package mundo.cartas;

public class cartaEpidemia extends carta {
    private final String descripcion;

    public cartaEpidemia(String descripcion) {
        super("Epidemia", "Epidemia");
        this.descripcion = descripcion;
    }
    public String ObtenerDescripcion() {
        return descripcion;
    }
}