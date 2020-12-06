package es.ucm.gdv.engine.windows;

import javax.swing.JFrame;

public class Input extends es.ucm.gdv.engine.AbstractInput {

    private ClickEvents _clickEvents;
    private MotionEvents _motionEvents;

    Input(JFrame jFrame){

        set_clickEvents(new ClickEvents(this));
        set_motionEvents(new MotionEvents(this));

        jFrame.addMouseListener(get_clickEvents());
        jFrame.addMouseMotionListener(get_motionEvents());
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PRIVADOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    synchronized void addEvent(TouchEvent e){
        _listTouchEvents.add(e);
    }

    public ClickEvents get_clickEvents() {
        return _clickEvents;
    }

    public void set_clickEvents(ClickEvents _clickEvents) {
        this._clickEvents = _clickEvents;
    }

    public MotionEvents get_motionEvents() {
        return _motionEvents;
    }

    public void set_motionEvents(MotionEvents _motionEvents) {
        this._motionEvents = _motionEvents;
    }
}
