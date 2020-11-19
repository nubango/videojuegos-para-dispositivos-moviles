package es.ucm.gdv.engine.windows;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Font;

public class Graphics extends JFrame implements es.ucm.gdv.engine.Graphics {

    public Graphics(String titulo)
    {
        super(titulo);
    }

    public boolean init()
    {
        setSize(480,640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        createBufferStrategy(2);    // creamos dos buffers
        _strategy = getBufferStrategy();
        */
        return true;
    }

    public void assignGraphics() {
        _graphics = _strategy.getDrawGraphics();
    }

    public void disposeGraphics() {
        _graphics.dispose();
    }



    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    /*
    @Override
    public void clear(Color color) {

    }
   */

    @Override
    public void translate(double x, double y) {

    }

    @Override
    public void scale(double x, double y) {

    }

    @Override
    public void rotate(double angle) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    /*
    @Override
    public void setColor(Color color) {

    }
    */

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    BufferStrategy _strategy;
    java.awt.Graphics _graphics;

}
