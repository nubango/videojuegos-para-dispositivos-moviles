package es.ucm.gdv.engine.windows;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {

    public Graphics()
    {
    }

    public void setGraphics(java.awt.Graphics2D graphics)
    {
        _graphics = graphics;
    }

    public void dispose()
    {
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
    public void drawLine(int x1, int y1, int x2, int y2)
    {
        _graphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2)
    {
        _graphics.fillRect(x1, y1, x2, y2);
    }

    @Override
    public void drawText(String text, int x, int y)
    {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    java.awt.Graphics2D _graphics;

}
