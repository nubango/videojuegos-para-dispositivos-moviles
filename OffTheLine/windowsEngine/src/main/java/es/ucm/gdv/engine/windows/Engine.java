package es.ucm.gdv.engine.windows;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.InputStream;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class Engine implements es.ucm.gdv.engine.Engine {

    public Engine() { }

    public boolean initApplication(String winTitle, int w, int h) {
        _ventana = new JFrame(winTitle);
        _ventana.setSize(w,h);
        _ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _ventana.setIgnoreRepaint(false);
        _ventana.setVisible(true);

        _graphics = new es.ucm.gdv.engine.windows.Graphics();

        return true;
    }

    @Override
    public void initLogic(Logic logic)
    {
        long lastFrameTime = System.nanoTime();

        _ventana.createBufferStrategy(2);

        BufferStrategy strategy = _ventana.getBufferStrategy();



        while(true)
        {
            long currentTime = System.nanoTime();
            long nanoDelta = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            _graphics.setGraphics((Graphics2D)strategy.getDrawGraphics());
            _graphics.setScaleFactor(_ventana.getSize().width, _ventana.getSize().height);

            logic.update(nanoDelta / 1.0E9);
            try{
                logic.render(_graphics);
            }
            finally{
                _graphics.dispose();
            }
            strategy.show();
        }
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

    private es.ucm.gdv.engine.windows.Graphics _graphics;
    private JFrame _ventana;
}
