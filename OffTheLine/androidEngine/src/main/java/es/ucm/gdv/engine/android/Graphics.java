package es.ucm.gdv.engine.android;

import android.graphics.Canvas;
import android.graphics.Paint;

import es.ucm.gdv.engine.Font;


public class Graphics extends es.ucm.gdv.engine.AbstractGraphics {

    public Graphics() {

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

/*        // Pintamos el fondo de negro
        _graphics.setBackground(Color.black);
        _graphics.clearRect(0,0, wReal, hReal);
*/
        translate(widthBlackBar, heightBlackBar);
        scale(scaleFactor, scaleFactor);
    }

    public void setCanvas(Canvas canvas)
    {
        _canvas = canvas;
    }
    public Canvas getCanvas(){ return _canvas; }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void translate(int x, int y) {
        _canvas.translate(x, y);
    }

    @Override
    public void scale(double x, double y) {
        _canvas.scale((float)x, (float)y);
    }

    @Override
    public void rotate(double angle) {
        _canvas.rotate((float)angle);
    }

    @Override
    public boolean save() {
        if(_canvas == null)
            return false;

        _canvas.save();
        return true;
    }

    @Override
    public void restore() {
        _canvas.restore();
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        _canvas.drawLine(x1, y1, x2, y2, _paint);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        Paint.Style s = _paint.getStyle();
        _paint.setStyle(Paint.Style.FILL);
        _canvas.drawRect(x1, y1, x2, y2, _paint);
        _paint.setStyle(s);
    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public void setColor(int color) {
        _paint.setColor(color);
    }

    @Override
    public void clear(int color) {
        _canvas.drawRGB((color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                (color & 0xff));
    }

    Canvas _canvas;
    Paint _paint = new Paint();
}
