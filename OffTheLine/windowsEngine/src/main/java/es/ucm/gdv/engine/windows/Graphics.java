package es.ucm.gdv.engine.windows;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class Graphics extends es.ucm.gdv.engine.AbstractGraphics {

    private java.awt.Graphics2D _graphics;
    private Queue<AffineTransform> _transformQueue;
    private Engine _engine;


    public Graphics(Engine engine)
    {
        _engine = engine;
        _transformQueue = new LinkedList<>();
    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    public void setGraphics(java.awt.Graphics2D graphics)
    {
        set_graphics(graphics);

        // Pintamos el fondo de negro
        get_graphics().setBackground(Color.black);
        get_graphics().clearRect(0,0, _wReal, _hReal);
    }

    public void dispose()
    {
        get_graphics().dispose();
    }

    // Se asigna la font creada por defecto al _graphics
    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        Font f = null;

        try {
            f = new Font(filename, size, isBold, _engine);
        } catch (FileNotFoundException e) {
            System.out.println("Fuente no cargada: fichero .ttf no encontrado");
        }

        return f;
    }

    @Override
    public void setFont(es.ucm.gdv.engine.Font f) {
        if (get_graphics() == null){
            System.out.println("Parte de Graphics sin inicializar: accediendo a _graphics antes de hacer run(Logic logic) ");
            return;
        }
        get_graphics().setFont(((Font)f).getFont());
    }

    @Override
    public void clear(int color) {
        Color c = new Color(color);
        get_graphics().setBackground(c);
        get_graphics().clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void translate(int x, int y)
    {
        get_graphics().translate(x, y);
    }

    @Override
    public void scale(double x, double y)
    {
        get_graphics().scale(x, y);
    }

    @Override
    public void rotate(double angle)
    {
        get_graphics().rotate(Math.toRadians(angle));
    }

    @Override
    public boolean save()
    {
        return get_transformQueue().offer(get_graphics().getTransform());
    }

    @Override
    public void restore()
    {
        AffineTransform t = get_transformQueue().poll();
        if (t != null)
            get_graphics().setTransform(t);
    }

    @Override
    public void setColor(int color) {
        Color c = new Color(color);
        get_graphics().setColor(c);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2)
    {
        get_graphics().drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2)
    {
        get_graphics().fillRect(x1, y1, x2, y2);
    }

    @Override
    public void drawText(String text, int x, int y)
    {
        if (get_graphics().getFont() != null) {
            get_graphics().drawString(text, x, y);
        }
        else {
            System.out.println("Establece una fuente con el método setFont(Font f)");
        }
    }


/* ---------------------------------------------------------------------------------------------- *
 * ------------------------------------- MÉTODOS PROTEGIDOS ------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    @Override
    protected void renderBlackBars() {
        super.renderBlackBars();
    }

    @Override
    protected void setScaleFactor(int wReal, int hReal) {
        super.setScaleFactor(wReal, hReal);
    }

    public java.awt.Graphics2D get_graphics() {
        return _graphics;
    }

    public void set_graphics(java.awt.Graphics2D _graphics) {
        this._graphics = _graphics;
    }

    public Queue<AffineTransform> get_transformQueue() {
        return _transformQueue;
    }

    public void set_transformQueue(Queue<AffineTransform> _transformQueue) {
        this._transformQueue = _transformQueue;
    }
}

