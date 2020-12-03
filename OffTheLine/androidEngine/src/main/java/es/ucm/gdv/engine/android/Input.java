package es.ucm.gdv.engine.android;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

public class Input extends es.ucm.gdv.engine.AbstractInput {

    private TouchEvents _touchEvents;

    Input(SurfaceView sv) {
        _touchEvents = new TouchEvents(this);
        sv.setOnTouchListener(_touchEvents);
    }

/* ---------------------------------------------------------------------------------------------- *
 * ------------------------------------- MÉTODOS PROTEGIDOS ------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    void addEvent(TouchEvent e) {
        _listTouchEvents.add(e);
    }

}