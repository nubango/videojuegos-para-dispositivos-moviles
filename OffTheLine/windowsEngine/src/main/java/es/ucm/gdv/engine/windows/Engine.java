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
        //_ventana.setUndecorated(true);
        _ventana.setSize(w, h);
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

        //_ventana.createBufferStrategy(2);
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                _ventana.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        else {
            // En "modo debug" podríamos querer escribir esto.
            System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }


        BufferStrategy strategy = _ventana.getBufferStrategy();

        _graphics.setGraphics((Graphics2D)strategy.getDrawGraphics());
        _graphics.setScaleFactor(_ventana.getSize().width, _ventana.getSize().height);

        while(true)
        {
            long currentTime = System.nanoTime();
            long nanoDelta = currentTime - lastFrameTime;
            lastFrameTime = currentTime;


            logic.update(nanoDelta / 1.0E9);

/*            try{
                logic.render(_graphics);
            }
            finally{
                _graphics.dispose();
            }
            strategy.show();
*/

            do {
                do {
                    //_ventana.setSize(_ventana.getSize().width + 1, _ventana.getSize().height + 3);

                    _graphics.setGraphics((Graphics2D)strategy.getDrawGraphics());
                    // un listener -> futuro
                    _graphics.setScaleFactor(_ventana.getSize().width, _ventana.getSize().height);
                    try {
                        logic.render(_graphics);
                    }
                    finally {
                        _graphics.dispose();
                    }
                } while(strategy.contentsRestored());
                strategy.show();
            } while(strategy.contentsLost());
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
