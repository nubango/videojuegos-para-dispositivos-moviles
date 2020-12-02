package es.ucm.gdv.engine.windows;

import javax.swing.JFrame;

public class Input extends es.ucm.gdv.engine.AbstractInput {

    ClickEvents _clickEvents;
    MotionEvents _motionEvents;

    Input(JFrame jFrame){

        _clickEvents = new ClickEvents(this);
        _motionEvents = new MotionEvents(this);

        jFrame.addMouseListener(_clickEvents);
        jFrame.addMouseMotionListener(_motionEvents);
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PRIVADOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    void addEvent(TouchEvent e){
        _listTouchEvents.add(e);
    }

}
