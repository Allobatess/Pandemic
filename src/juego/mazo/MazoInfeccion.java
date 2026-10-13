package juego.mazo;

import mundo.Ciudad;
import mundo.cartas.CartaInfeccion;
import estructurasDatos.SinglyLinkedListQueue;
import datosObjetos.InfoCartasInfeccion;

/**
 * Administra el mazo principal de infección (Array Stack) y la pila de descartes 
 * utilizando la estructura de datos externa SinglyLinkedListQueue (FIFO).
 */
public class MazoInfeccion {

    private final CartaInfeccion[] mazoPrincipal;
    private int tope; // Puntero para comportamiento Stack LIFO
    private final SinglyLinkedListQueue<CartaInfeccion> colaDescarte; // Instancia de la estructura externa

    public MazoInfeccion(Ciudad[] ciudades) {
        CartaInfeccion[] creadas = InfoCartasInfeccion.crearCartas(ciudades);
        this.mazoPrincipal = new CartaInfeccion[creadas.length];
        for (int i = 0; i < creadas.length; i++) {
            this.mazoPrincipal[i] = creadas[i];
        }
        this.tope = creadas.length - 1;
        this.colaDescarte = new SinglyLinkedListQueue<>();
        mezclar();
    }

    public void mezclar() {
        for (int i = tope; i > 0; i--) {
            int aleatorio = (int) (Math.random() * (i + 1));
            CartaInfeccion temp = mazoPrincipal[i];
            mazoPrincipal[i] = mazoPrincipal[aleatorio];
            mazoPrincipal[aleatorio] = temp;
        }
    }

    public CartaInfeccion robarCarta() {
        if (tope >= 0) {
            CartaInfeccion robada = mazoPrincipal[tope];
            mazoPrincipal[tope] = null;
            tope--;
            colaDescarte.enqueue(robada); // Encola en el descarte FIFO
            return robada;
        }
        return null;
    }

    //  extrae la carta más antigua del descarte (queue)
    public CartaInfeccion extraerCartaDescarteFIFO() {
        return colaDescarte.dequeue();
    }

    public void prepararInfeccionInicial() {
        // Infecta 3 ciudades con 3 cubos, 3 ciudades con 2 cubos y 3 ciudades con 1 cubo
        for (int cantidadCubos = 3; cantidadCubos >= 1; cantidadCubos--) {
            for (int i = 0; i < 3; i++) {
                CartaInfeccion robada = robarCarta();
                if (robada != null) {
                    Ciudad c = robada.obtenerCiudad();
                    for (int k = 0; k < cantidadCubos; k++) {
                        c.agregarCuboDeInfeccion();
                    }
                }
            }
        }
    }

    public int cartasRestantes() {
        return tope + 1;
    }
}