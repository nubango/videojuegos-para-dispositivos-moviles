package es.ucm.gdv.engine.android;

import java.io.InputStream;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class Engine implements es.ucm.gdv.engine.Engine {
    @Override
    public void initLogic(Logic logic) {

    }

    @Override
    public Graphics getGraphics() {
        return null;
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
