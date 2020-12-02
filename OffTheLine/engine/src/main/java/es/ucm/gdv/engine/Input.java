package es.ucm.gdv.engine;

/*
 * Proporciona las funcionalidades ed entrada basicas
 */

import java.util.List;

public interface Input {

    enum Type { press, release, displace }

    /**
     * Clase que representa la informacion de un toque sobre la pantalla o evento de raton
     **/
    class TouchEvent {

        public Type _type;
        public int _x, _y;
        public int _fingerId;

        public TouchEvent(Type t, int x, int y, int fingerId){
            _type = t;
            _x = x;
            _y = y;
            _fingerId = fingerId;
        }
    }

    // Devuelve la lista de eventos recibidos desde la ultima invocacion
    List<TouchEvent> getTouchEvents();

}
