package es.ucm.gdv.engine.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MotionEvents implements MouseMotionListener {

    private Input _input;

    MotionEvents(Input input){
        set_input(input);
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Arrastrar el ratón con el botón pulsado
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        get_input().addEvent(new Input.TouchEvent(Input.Type.displace, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public Input get_input() {
        return _input;
    }

    public void set_input(Input _input) {
        this._input = _input;
    }
}
