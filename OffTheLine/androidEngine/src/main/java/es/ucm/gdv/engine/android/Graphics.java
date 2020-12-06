package es.ucm.gdv.engine.android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
CUIDADO con el color en android porque es ARGB (creo, por lo menos el alfa son los 2 primeros números por lo que si son 0 no se pintará nada

Clase que contiene los métodos necesarios para pintar una linea, un rectángulo relleno, texto y limpiar la pantalla.
*/



public class Graphics extends es.ucm.gdv.engine.AbstractGraphics {

    private Canvas _canvas;
    private Paint _paint = new Paint();
    private AssetManager _assetManager;

    public Graphics(AssetManager assetManager) {
        set_assetManager(assetManager);
    }


/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
    * Método que asigna un canvas nuevo.
    * Al ser nuevo tenemos que decirle el color de fondo y aplicamos la traslación y escalado para
    * que se pinte en el lugar correcto de la pantalla reescalada
    * */
    public void setCanvas(Canvas canvas)
    {
        set_canvas(canvas);
        // decimos el grosor de la linea
        get_paint().setStrokeWidth(1f);
        // Pintamos el fondo de negro
        get_canvas().drawRGB(0xFF,0xFF,0xFF);

        // aplicamos la traslación y el escalado
        translate(widthBlackBar, heightBlackBar);
        scale(scaleFactor, scaleFactor);
    }

    public Canvas getCanvas(){ return get_canvas(); }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        Font f = new Font(get_assetManager(), filename, size, isBold);
        return f;
    }

    @Override
    public void setFont(es.ucm.gdv.engine.Font f) {
        if (f != null) {
            // Tenemos fuente. Vamos a escribir texto.
            // Preparamos la configuración de formato en el
            // objeto _paint que utilizaremos en cada frame.
            get_paint().setTypeface(((Font) f).getFont());
            get_paint().setFakeBoldText(((Font) f).isBold());
            get_paint().setTextSize(((Font) f).getSize());
        }
    }

    @Override
    public void translate(int x, int y) {
        get_canvas().translate(x, y);
    }

    @Override
    public void scale(double x, double y) {
        get_canvas().scale((float)x, (float)y);
    }

    @Override
    public void rotate(double angle) {
        get_canvas().rotate((float)angle);
    }

    @Override
    public boolean save() {
        if(get_canvas() == null)
            return false;

        get_canvas().save();
        return true;
    }

    @Override
    public void restore() {
        if(get_canvas() != null)
            get_canvas().restore();
        else
            System.out.println("***********EL OBJETO '_canvas' ES NULL. NO SE PUEDE HACER EL RESTORE***********");
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        get_canvas().drawLine(x1, y1, x2, y2, get_paint());
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        float g = get_paint().getStrokeWidth();
        get_paint().setStrokeWidth(0);
        get_canvas().drawRect(x1, y1, x2, y2, get_paint());
        get_paint().setStrokeWidth(g);
    }

    @Override
    public void drawText(String text, int x, int y) {
        get_canvas().drawText(text, x, y, get_paint());
    }

    @Override
    public void setColor(int color) {
        get_paint().setColor(color);
    }

    @Override
    public void clear(int color) {
        get_canvas().drawRGB((color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                (color & 0xff));
    }

/* ---------------------------------------------------------------------------------------------- *
 * ------------------------------------- MÉTODOS PROTEGIDOS ------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Clase que pinta unas bandas negras a los lados debido al reescalado de la pantalla
     * Lo sobreescribimos para poder llamarlo desde La clase MainLoop
     */
    @Override
    protected void renderBlackBars() {
        super.renderBlackBars();
    }

    /**
     * Clase que reescala el tamaño de la pantalla lógica para que se vea bien en todas las resoluciones
     * Lo sobreescribimos para poder llamarlo desde La clase MainLoop
     */
    @Override
    protected void setScaleFactor(int wReal, int hReal) {
        super.setScaleFactor(wReal, hReal);
    }

    public Canvas get_canvas() {
        return _canvas;
    }

    public void set_canvas(Canvas _canvas) {
        this._canvas = _canvas;
    }

    public Paint get_paint() {
        return _paint;
    }

    public void set_paint(Paint _paint) {
        this._paint = _paint;
    }

    public AssetManager get_assetManager() {
        return _assetManager;
    }

    public void set_assetManager(AssetManager _assetManager) {
        this._assetManager = _assetManager;
    }
}
