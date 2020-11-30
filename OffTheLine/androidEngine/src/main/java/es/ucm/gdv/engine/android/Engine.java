package es.ucm.gdv.engine.android;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;

import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class Engine extends SurfaceView implements es.ucm.gdv.engine.Engine {



    SurfaceHolder _holder;
    private ActiveRendering _activeRendering;
    private es.ucm.gdv.engine.android.Graphics _graphics;

    /**
     * Constructor.
     *
     * @param context Contexto en el que se integrará la vista
     *                (normalmente una actividad).
     */
    public Engine(Context context, Logic logic) {

        super(context);
        _holder = getHolder();

        _activeRendering = new ActiveRendering(logic, this);

        _graphics = new es.ucm.gdv.engine.android.Graphics();
/*        if (_font != null) {
            // Tenemos fuente. Vamos a escribir texto.
            // Preparamos la configuración de formato en el
            // objeto _paint que utilizaremos en cada frame.
            _paint.setTypeface(_font);
            _paint.setFakeBoldText(true);
            _paint.setColor(0xFFFFFFFF);
            _paint.setTextSize(80);

        }*/

    } // Engine

    public SurfaceHolder get_holder() {
        return _holder;
    }

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {
        _activeRendering.resume();
    } // resume
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {
        _activeRendering.pause();
    } // pause

    @Override
    public es.ucm.gdv.engine.android.Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public InputStream openInputStream(String filename) {
        return null;
    }

}
