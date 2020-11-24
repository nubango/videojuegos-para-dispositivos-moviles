package es.ucm.gdv.engine;

public abstract class AbstractGraphics implements Graphics {

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

    private int _height = 480, _width = 640;
}
