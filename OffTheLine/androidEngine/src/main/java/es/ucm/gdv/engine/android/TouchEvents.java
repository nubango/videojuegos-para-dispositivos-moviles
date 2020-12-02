package es.ucm.gdv.engine.android;

import android.view.MotionEvent;
import android.view.View;


public class TouchEvents implements View.OnTouchListener  {

    private Input _input;

    TouchEvents(Input input){
        _input = input;
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Input.Type type = parseActionToType(event.getActionMasked());
        if (type == null)
            return false;

        int x = (int)event.getX();
        int y = (int)event.getY();
        int fingerId = event.getActionIndex();

        _input.getListEvents().add(new Input.TouchEvent(type, x, y, fingerId));

        return true;
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PRIVADOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    private Input.Type parseActionToType(int action){

        switch (action){
            case MotionEvent.ACTION_DOWN:
                return Input.Type.press;
            case MotionEvent.ACTION_UP:
                return Input.Type.release;
            case MotionEvent.ACTION_MOVE:
                return Input.Type.displace;
        }

        return null;
    }
}
