package es.ucm.gdv.engine.windows;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.Queue;

import es.ucm.gdv.engine.Font;

public class Graphics extends es.ucm.gdv.engine.AbstractGraphics {

    public Graphics()
    {
        _transformQueue = new LinkedList<>();
    }

    @Override
    protected void renderBlackBars() {
        super.renderBlackBars();
    }

    @Override
    protected void setScaleFactor(int wReal, int hReal) {
        super.setScaleFactor(wReal, hReal);
    }

    public void setGraphics(java.awt.Graphics2D graphics)
    {
        _graphics = graphics;

        // Pintamos el fondo de negro
        _graphics.setBackground(Color.black);
        _graphics.clearRect(0,0, _wReal, _hReal);

    }

    public void dispose()
    {
        _graphics.dispose();
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        Color c = new Color(color);
        _graphics.setBackground(c);
        _graphics.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void translate(int x, int y)
    {
        _graphics.translate(x, y);
    }

    @Override
    public void scale(double x, double y)
    {
        _graphics.scale(x, y);
    }

    @Override
    public void rotate(double angle)
    {
        _graphics.rotate(angle);
    }

    @Override
    public boolean save()
    {
        return _transformQueue.offer(_graphics.getTransform());
    }

    @Override
    public void restore()
    {
        AffineTransform t = _transformQueue.poll();
        if (t != null)
            _graphics.setTransform(t);
    }

    @Override
    public void setColor(int color) {
        Color c = new Color(color);
        _graphics.setColor(c);
    }

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

    java.awt.Graphics2D _graphics;
    Queue<AffineTransform> _transformQueue;

}
