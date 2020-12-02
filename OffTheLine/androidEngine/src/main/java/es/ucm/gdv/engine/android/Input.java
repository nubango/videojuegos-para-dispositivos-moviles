package es.ucm.gdv.engine.android;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class Input extends es.ucm.gdv.engine.AbstractInput implements View.OnTouchListener {

    Input(SurfaceView sv) {
        sv.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Type type = parseActionToType(event.getActionMasked());
        if (type == null)
            return false;

        int x = (int)event.getX();
        int y = (int)event.getY();
        int fingerId = event.getActionIndex();

        _touchEvents.add(new TouchEvent(type, x, y, fingerId));

        return true;
    }

    private Type parseActionToType(int action){

        switch (action){
            case MotionEvent.ACTION_DOWN:
                return Type.press;
            case MotionEvent.ACTION_UP:
                return Type.release;
            case MotionEvent.ACTION_MOVE:
                return Type.displace;
        }

        return null;
    }
}