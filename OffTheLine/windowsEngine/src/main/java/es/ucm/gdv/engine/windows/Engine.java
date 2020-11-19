package es.ucm.gdv.engine.windows;

import java.io.InputStream;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Engine implements es.ucm.gdv.engine.Engine {

    public boolean init(String winTitle) {
        _graphics = new es.ucm.gdv.engine.windows.Graphics(winTitle);
        _graphics.init();
        _graphics.setIgnoreRepaint(false);
        _graphics.setVisible(true);
        return true;
    }

    @Override
    public Graphics getGraphics() {
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

    es.ucm.gdv.engine.windows.Graphics _graphics;
}
