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
        //_offsetBar = 58;
        _offsetBar = 71;
    }

    /*
    * DUDAS:
    * Hemos hecho las cuentas del reescalado pero no sabemos muy bien cómo usar los números.
	*   - Lo primero, en PC cuando se tiene que hacer el reescalado? en cada vuelta de bucle?
	*   - Donde aplicamos las cuentas y como? en el método de drawLine aplicamos la traslación
	*   traslate(w, h) y el escalado scale(x,x) o no usamos esos métodos y lo que hacemos es
	*   directamente multiplicar por el factor de escala y trasladar los puntos?
    * */
    void setScaleFactor(int wReal, int hReal) {

        // valor que indica el factor de escala con el que rescalaremos la pantalla
        double scaleFactor;

        // tamaño de las barras negras verticales y horizontales
        int widthBlackBar, heightBlackBar;

        // pixeles reales que ocupa la pantalla lógica (ya rescalada)
        int widthSizeScreen, heightSizeScreen;

        // factor de escala horizontal y vertical (solo elegimos uno, el más pequeño, para rescalar la pantalla)
        double wFactor, hFactor;

        // getWidth y getHeight son el tamaño lógico de la pantalla (setLogicSize)
        wFactor = (double)wReal / (double)getWidth();
        hFactor = (double)hReal / (double)getHeight();

        // si hemos escogido el wFactor, el width de la pantalla ocupa el width de la ventana entero
        if (wFactor < hFactor) {
            scaleFactor = wFactor;

            widthSizeScreen =  wReal;
            heightSizeScreen = ((wReal * getHeight()) / getWidth());
        }
        // si hemos escogido el hFactor, el height de la pantalla ocupa el height de la ventana entero
        else {
            scaleFactor = hFactor;

            widthSizeScreen =  (hReal * getWidth()) / getHeight();
            heightSizeScreen = hReal;
        }

        // calculamos lo que miden las barras negras tanto superior como inferior
        widthBlackBar = (wReal - widthSizeScreen) / 2;
        heightBlackBar = ((hReal - heightSizeScreen) / 2);

        // Pintamos el fondo de negro
        _graphics.setBackground(Color.black);
        _graphics.clearRect(0,0, wReal, hReal);

        translate(widthBlackBar, heightBlackBar);
        scale(scaleFactor, scaleFactor);
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

    @Override
    public void clear(Color color) {
        _graphics.setBackground(color);
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
    public void setColor(Color color) {
        _graphics.setColor(color);
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
    private int _offsetBar;
}
