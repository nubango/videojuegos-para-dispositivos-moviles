package es.ucm.gdv.engine;

import java.awt.Color;

public abstract class AbstractGraphics implements Graphics {

    private int _height = 680, _width = 640;

    // valor que indica el factor de escala con el que rescalaremos la pantalla
    protected double scaleFactor;

    // tamaño de las barras negras verticales y horizontales
    protected int widthBlackBar, heightBlackBar;

    // pixeles reales que ocupa la pantalla lógica (ya rescalada)
    protected int widthSizeScreen, heightSizeScreen;

    // dimensiones reales de la pantalla
    protected int _wReal, _hReal;

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    @Override
    public void setLogicSize(int w, int h) {
        _height = h;
        _width = w;
    }

    @Override
    public int getWidth() {
        return _width;
    }

    @Override
    public int getHeight() {
        return _height;
    }

    @Override
    public int getWidthBlackBar(){ return widthBlackBar; }

    @Override
    public int getHeightBlackBar(){ return heightBlackBar; }

    @Override
    public double getScaleFactor(){ return scaleFactor; }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PRIVADOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    protected void renderBlackBars() {
        if (save()) {
            setColor(0xFF000000);
            scale(1 / scaleFactor, 1 / scaleFactor);
            translate(-widthBlackBar, -heightBlackBar);

            // Primera barra: uno de los dos no va a pintar nada porque o widthBlackBar o heightBlackBar son 0
            fillRect(0, 0, widthBlackBar, heightSizeScreen);
            fillRect(0, 0, widthSizeScreen, heightBlackBar);

            // Segunda barra:
            fillRect(widthBlackBar + widthSizeScreen, 0, _wReal, _hReal);
            fillRect(0, heightBlackBar + heightSizeScreen, _wReal, _hReal);
        }
        restore();
    }

    protected void setScaleFactor(int wReal, int hReal) {

        _wReal = wReal;
        _hReal = hReal;
        // factor de escala horizontal y vertical (solo elegimos uno, el más pequeño, para rescalar la pantalla)
        double wFactor, hFactor;

        // getWidth y getHeight son el tamaño lógico de la pantalla (setLogicSize)
        wFactor = (double) wReal / (double) _width;
        hFactor = (double) hReal / (double) _height;

        // si hemos escogido el wFactor, el width de la pantalla ocupa el width de la ventana entero
        if (wFactor < hFactor) {
            scaleFactor = wFactor;

            widthSizeScreen = wReal;
            heightSizeScreen = ((wReal * _height) / _width);
        }
        // si hemos escogido el hFactor, el height de la pantalla ocupa el height de la ventana entero
        else {
            scaleFactor = hFactor;

            widthSizeScreen = (hReal * _width) / _height;
            heightSizeScreen = hReal;
        }

        // calculamos lo que miden las barras negras tanto superior como inferior
        widthBlackBar = (wReal - widthSizeScreen) / 2;
        heightBlackBar = ((hReal - heightSizeScreen) / 2);

        translate(widthBlackBar, heightBlackBar);
        scale(scaleFactor, scaleFactor);
    }
}
