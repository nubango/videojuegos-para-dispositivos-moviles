package es.ucm.gdv.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements es.ucm.gdv.engine.Input {

    protected List<TouchEvent> _listTouchEvents = null;

    protected AbstractInput(){
        _listTouchEvents = new ArrayList();
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PUBLICOS  ------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Devuelve la lista de eventos. Si no hay eventos devuelve null.
     * */
    @Override
    synchronized public List<TouchEvent> getTouchEvents() {
        if (_listTouchEvents.isEmpty())
            return null;

        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>(_listTouchEvents);
        _listTouchEvents.clear();

        return touchEvents;
    }

    synchronized protected void addEvent(TouchEvent e){
        _listTouchEvents.add(e);
    }
}
