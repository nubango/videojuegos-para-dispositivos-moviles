package es.ucm.gdv.engine;

/*
 * Proporciona las funcionalidades ed entrada basicas
 */

import java.util.List;

public interface Input {

    /*
     * Clase que representa la informacion de un toque sobre la pantalla o evento de raton
     */
    class TouchEvent {

    }

    // Devuelve la lista de eventos recibidos desde la ultima invocacion
    List<TouchEvent> getTouchEvents();

}
