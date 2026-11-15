package mundo.roles;

import juego.LogicaJuego;
import mundo.Ciudad;
import mundo.Personaje;
import mundo.cartas.Carta;
import mundo.cartas.CartaCiudad;
import java.util.Scanner;

public class ExpertoOperaciones extends Rol {

    public ExpertoOperaciones() {
        super(
            "Experto en Operaciones",
            "Como acción, puede construir un centro de investigación en su ciudad actual sin descartar carta, y moverse desde un centro a cualquier ciudad descartando cualquier carta de ciudad."
        );
    }

    @Override
    public int ejecutarMenu(Personaje personaje, LogicaJuego juego) {
        Scanner scanner = personaje.obtenerScanner();

        imprimirOpcionesBase();
        System.out.println("  9. [Habilidad] Volar desde centro de investigación a cualquier ciudad (stationToAnyDirectMove)");
        System.out.println("  0. Pasar / Finalizar acciones restantes");
        System.out.print("Opción: ");

        int opcion = leerEntero(scanner);
        if (opcion == Integer.MIN_VALUE) {
            System.out.println("Entrada inválida. Ingrese un número válido.");
            return 0;
        }

        if (opcion == 9) {
            return ejecutarStationToAnyDirectMove(personaje, juego) ? 1 : 0;
        }

        return procesarOpcionBase(opcion, personaje, juego);
    }

    @Override
    public boolean construirCentro(Personaje personaje, LogicaJuego juego) {
        Ciudad ciudadActual = personaje.obtenerCiudadActual();

        if (ciudadActual.tenerCentroDeInvestigacion()) {
            System.out.println("Esta ciudad ya cuenta con un centro de investigación.");
            return false;
        }

        if (juego.puedeConstruirCentro()) {
            juego.registrarCentroDeInvestigacion(ciudadActual);
            System.out.println("¡Habilidad de Experto en Operaciones! Centro de investigación construido en "
                    + ciudadActual.obtenerNombre() + " sin descartar carta ("
                    + juego.obtenerCentrosConstruidos() + "/6).");
            return true;
        } else {
            Ciudad origen = seleccionarCentroAReposicionar(personaje, juego);
            if (origen == null) {
                return false;
            }
            juego.moverCentroDeInvestigacion(origen, ciudadActual);
            System.out.println("¡Habilidad de Experto en Operaciones! Centro reposicionado de "
                    + origen.obtenerNombre() + " a " + ciudadActual.obtenerNombre() + " sin descartar carta (6/6).");
            return true;
        }
    }

    public boolean stationToAnyDirectMove(Personaje personaje, int indiceCartaCiudad, Ciudad ciudadDestino, LogicaJuego juego) {
        Ciudad ciudadActual = personaje.obtenerCiudadActual();

        if (!ciudadActual.tenerCentroDeInvestigacion()) {
            System.out.println("Debes estar en una ciudad con centro de investigación para usar esta habilidad.");
            return false;
        }

        if (ciudadDestino == null || ciudadDestino == ciudadActual) {
            System.out.println("Ciudad destino inválida.");
            return false;
        }

        if (indiceCartaCiudad < 0 || indiceCartaCiudad >= personaje.obtenerNumCartas()) {
            System.out.println("Índice de carta inválido.");
            return false;
        }

        Carta carta = personaje.obtenerMano()[indiceCartaCiudad];
        if (!(carta instanceof CartaCiudad)) {
            System.out.println("Debes seleccionar una Carta de Ciudad para descartar.");
            return false;
        }

        personaje.removerCartaPorIndice(indiceCartaCiudad);
        juego.cambiarUbicacion(personaje, ciudadDestino);
        return true;
    }

    private boolean ejecutarStationToAnyDirectMove(Personaje personaje, LogicaJuego juego) {
        if (!personaje.obtenerCiudadActual().tenerCentroDeInvestigacion()) {
            System.out.println("Debes encontrarte en un centro de investigación para usar esta habilidad.");
            return false;
        }

        if (personaje.obtenerNumCartas() == 0) {
            System.out.println("No tienes cartas en la mano.");
            return false;
        }

        Scanner scanner = personaje.obtenerScanner();
        System.out.print("Ingrese el índice de cualquier Carta Ciudad de su mano a descartar: ");
        int idxCarta = leerEntero(scanner);
        if (idxCarta == Integer.MIN_VALUE) {
            System.out.println("Índice inválido.");
            return false;
        }

        System.out.print("Ingrese el nombre exacto de la ciudad destino: ");
        String nombreDestino = scanner.nextLine().trim();
        Ciudad destino = juego.buscarCiudad(nombreDestino);

        if (destino == null) {
            System.out.println("Ciudad no encontrada en el tablero.");
            return false;
        }

        return stationToAnyDirectMove(personaje, idxCarta, destino, juego);
    }
}