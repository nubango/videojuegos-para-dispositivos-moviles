package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.gdv.engine.Logic;

public class Engine extends SurfaceView implements es.ucm.gdv.engine.Engine {



    SurfaceHolder _holder;
    private ActiveRendering _activeRendering;
    private Graphics _graphics;
    private Input _input;
    private AssetManager _assetManager;

    /**
     * Constructor.
     *
     * @param context Contexto en el que se integrará la vista
     *                (normalmente una actividad).
     */
    public Engine(Context context,  Logic logic) {
        super(context);
        _assetManager = context.getAssets();
        _holder = getHolder();
        _activeRendering = new ActiveRendering(logic, this);
        _graphics = new Graphics(_assetManager);
        _input = new Input(this);
    } // Engine

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
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    @Override
    public InputStream openInputStream(String filename) {
        try {
            return _assetManager.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*****Ruta del fichero " + filename + " no encontrado*****");
        return null;
    }

}
