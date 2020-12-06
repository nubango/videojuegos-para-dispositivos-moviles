package es.ucm.gdv.engine.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickEvents implements MouseListener {

    private Input _input;

    ClickEvents(Input input) {
        set_input(input);
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Si pulsas un botón, arrastras fuera del botón y sueltas no se ejecutara el click()
     * */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    /**
     * mouseEvent.getButton() => NOBUTTON (0) - sin pulsación, BUTTON1 (1) - derecho,
     *                           BUTTON2 (2) - centro, BUTTON3 (3) - izquierdo
     * */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        get_input().addEvent(new Input.TouchEvent(Input.Type.press, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    /**
     * La acción de pulsar se ejecuta al sontar
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        get_input().addEvent(new Input.TouchEvent(Input.Type.release, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    public Input get_input() {
        return _input;
    }

    public void set_input(Input _input) {
        this._input = _input;
    }
}
