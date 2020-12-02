package es.ucm.gdv.engine.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MotionEvents implements MouseMotionListener {

    Input _input;

    MotionEvents(Input input){
        _input = input;
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Arrastrar el ratón con el botón pulsado
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        _input.addEvent(new Input.TouchEvent(Input.Type.displace, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
