package mundo.roles;

public class PlanificadorContingencias extends Rol {

    public PlanificadorContingencias() {
        super(
            "Planificador de Contingencias",
            "Como acción, puede tomar una carta de Evento de la pila de descartes y guardarla en su carta de rol (máximo 1 y no cuenta para el límite de mano). Al jugarla, la carta se retira del juego."
        );
    }
}