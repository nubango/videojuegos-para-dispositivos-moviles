package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.gdv.engine.Logic;

public class Engine implements es.ucm.gdv.engine.Engine {

    private SurfaceView _surfaceView;
    private MainLoop _mainLoop;
    private Graphics _graphics;
    private Input _input;
    private AssetManager _assetManager;
    private Logic _logic;

    /**
     * Constructor.
     *
     * @param context Contexto en el que se integrará la vista
     *                (normalmente una actividad).
     */
    public Engine(Context context) {
        _surfaceView = new SurfaceView(context);
        _assetManager = context.getAssets();
        _mainLoop = new MainLoop(this);
        _graphics = new Graphics(_assetManager);
        _input = new Input(_surfaceView, _graphics);
    } // Engine

/* ---------------------------------------------------------------------------------------------- *
 * ------------------------------------- MÉTODOS PROTEGIDOS ------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    protected Logic getLogic(){ return _logic; }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {
        _mainLoop.resume();
    } // resume

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
        _mainLoop.pause();
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
    public InputStream openInputStream(String filename) throws IOException {
        return _assetManager.open(filename);
    }

    public SurfaceView getSurfaceView(){ return _surfaceView; }

    @Override
    public void setLogic(Logic l){ _logic = l; _mainLoop._initLogic = true;}

}
