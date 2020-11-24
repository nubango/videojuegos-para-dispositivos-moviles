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
        // cantidad de pixeles que ocupa la barra superior de la ventana
        _offsetBar = 58;
    }

    void setScaleFactor(int wReal, int hReal) {
        _widthSizeWindow = wReal;
        _heightSizeWindow = hReal;

        // factor de escala: hay dos y escogemos el más pequeño porque es el "mínimo común múltiplo entre los dos"
        double wFactor = wReal / (float)getWidth();
        double hFactor = hReal / (float)getHeight();

        _scaleFactor = Math.min(wFactor, hFactor);

        // si hemos escogido el wFactor, el width de la pantalla ocupa el width de la ventana entero
        if (wFactor < hFactor) {
            _scaleFactor = wFactor;

            _widthSizeScreen =  wReal;
            _heightSizeScreen = ((wReal * getHeight()) / getWidth());
        }
        // si hemos escogido el hFactor, el height de la pantalla ocupa el height de la ventana entero
        else {
            _scaleFactor = hFactor;

            _widthSizeScreen =  (hReal * getWidth()) / getHeight();;
            _heightSizeScreen = hReal;
        }

        // calculamos lo que miden las barras negras tanto superior como inferior
        _widthBlackBar = (wReal - _widthSizeScreen) / 2;
        _heightBlackBar = ((hReal - _heightSizeScreen) / 2);

/*
        scale(_scaleFactor, _scaleFactor);

        // offset baja el origen de coordenadas para que empiece por debajo de la barra de la ventana
        translate(_widthBlackBar, _heightBlackBar+_offsetBar);
*/

    }

    public void setGraphics(java.awt.Graphics2D graphics)
    {
        _graphics = graphics;

        // debug
        _graphics.setColor(Color.black);
        _graphics.fillRect(0, 0, _widthSizeWindow, _heightSizeWindow);
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
    public void clear(Color color) {

        // Debug
        Color c = _graphics.getColor();
        _graphics.setColor(color);
        _graphics.fillRect(_widthBlackBar, _heightBlackBar, _widthSizeScreen , _heightSizeScreen);
        _graphics.setColor(c);
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
    public void setColor(Color color) {
        _graphics.setColor(color);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2)
    {
        int sx1, sy1, sx2, sy2;
        sx1 = (int)(x1*_scaleFactor) + _widthBlackBar;
        sy1 = (int)(y1*_scaleFactor) + _heightBlackBar;
        sx2 = (int)(x2*_scaleFactor) + _widthBlackBar;
        sy2 = (int)(y2*_scaleFactor) + _heightBlackBar;

        _graphics.drawLine(sx1, sy1, sx2, sy2);
        //_graphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2)
    {

        int sx1, sy1, sx2, sy2;
        sx1 = (int)(x1*_scaleFactor) + _widthBlackBar;
        sy1 = (int)(y1*_scaleFactor) + _heightBlackBar;
        sx2 = (int)(x2*_scaleFactor) + _widthBlackBar;
        sy2 = (int)(y2*_scaleFactor) + _heightBlackBar;

        _graphics.fillRect(sx1, sy1, sx2, sy2);
       // _graphics.fillRect(x1, y1, x2, y2);

    }

    @Override
    public void drawText(String text, int x, int y)
    {

    }

    java.awt.Graphics2D _graphics;
    Queue<AffineTransform> _transformQueue;
    private int _offsetBar;
    private double _scaleFactor;
    private int _widthBlackBar, _heightBlackBar;
    private int _widthSizeScreen, _heightSizeScreen;
    private int _widthSizeWindow, _heightSizeWindow;
}
