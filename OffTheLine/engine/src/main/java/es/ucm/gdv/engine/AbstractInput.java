package es.ucm.gdv.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractInput implements es.ucm.gdv.engine.Input {

    protected AbstractInput(){
        _touchEvents = new ArrayList();
    }

    /**
     * Devuelve la lista de eventos. Si no hay eventos devuelve null.
     * */
    @Override
    public List<TouchEvent> getTouchEvents() {
        if (_touchEvents.isEmpty())
            return null;

        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>(_touchEvents);
        _touchEvents.clear();

        return touchEvents;
    }

    protected List<TouchEvent> _touchEvents = null;
}
