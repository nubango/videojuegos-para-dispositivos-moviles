package es.ucm.gdv.engine.android;

import android.graphics.Canvas;
import android.graphics.Paint;

import es.ucm.gdv.engine.Font;

/*
CUIDADO con el color en android porque es ARGB (creo, por lo menos el alfa son los 2 primeros números por lo que si son 0 no se pintyará nada

Clase que contiene los métodos necesarios para pintar una linea, un rectángulo relleno y limpiar la pantalla.
*/



public class Graphics extends es.ucm.gdv.engine.AbstractGraphics {

    public Graphics() {

    }

    /*
     * Clase que pinta unas bandas negras a los lados debido al reescalado de la pantalla
     * Lo sobreescribimos para poder llamarlo desde La clase ActiveRendering
     */
    @Override
    protected void renderBlackBars() {
        super.renderBlackBars();
    }

    /*
     * Clase que reescala el tamaño de la pantalla lógica para que se vea bien en todas las resoluciones
     * Lo sobreescribimos para poder llamarlo desde La clase ActiveRendering
     */
    @Override
    protected void setScaleFactor(int wReal, int hReal) {
        super.setScaleFactor(wReal, hReal);
    }

    /*
    * Método que asigna un canvas nuevo.
    * Al ser nuevo tenemos que decirle el color de fondo y aplicamos la traslación y escalado para
    * que se pinte en el lugar correcto de la pantalla reescalada
    * */
    public void setCanvas(Canvas canvas)
    {
        _canvas = canvas;
        // decimos el grosor de la linea
        _paint.setStrokeWidth(1f);
        // Pintamos el fondo de negro
        _canvas.drawRGB(0xFF,0xFF,0xFF);

        // aplicamos la traslación y el escalado
        translate(widthBlackBar, heightBlackBar);
        scale(scaleFactor, scaleFactor);
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
        if(_canvas != null)
            _canvas.restore();
        else
            System.out.println("***********EL OBJETO '_canvas' ES NULL. NO SE PUEDE HACER EL RESTORE***********");
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        _canvas.drawLine(x1, y1, x2, y2, _paint);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        float g = _paint.getStrokeWidth();
        _paint.setStrokeWidth(0);
        _canvas.drawRect(x1, y1, x2, y2, _paint);
        _paint.setStrokeWidth(g);
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

    //--------------- ATRIBUTOS ---------------//
    Canvas _canvas;
    Paint _paint = new Paint();

}
