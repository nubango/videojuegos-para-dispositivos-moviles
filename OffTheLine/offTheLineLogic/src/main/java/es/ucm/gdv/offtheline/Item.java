package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Item {

    private Utils.Point _position;
    private int _tam = 4;           // Tamaño de los segmentos que forman el item
    private int _scale = 2;
    private int _color = 0xFFFFEF00;

    private double _speed = 150;    // Velocidad de rotacion
    private double _angle = 0;      // Angulo de giro

    boolean _taken = false;

    Item(double x, double y) {
        _position = new Utils.Point(x, y);
    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;
    }

    void render(Graphics g) {
        g.setColor(_color);
        /*
        if(g.save()) {
            g.translate((int)_position.x,(int)_position.y);
            g.scale(_scale, _scale);
            g.rotate(_angle);
        }


        g.drawLine(-_tam,-_tam, _tam,-_tam);
        g.drawLine(_tam,-_tam,_tam, _tam );
        g.drawLine(_tam,_tam, -_tam,_tam);
        g.drawLine(-_tam,_tam, -_tam,-_tam);

        g.restore();
        */
        if(!g.save())
            return;
        g.translate((int)_position.x + OffTheLineLogic.LOGIC_WIDTH / 2,-(int)_position.y +
                OffTheLineLogic.LOGIC_HEIGHT / 2);
        g.scale(_scale, _scale);
        g.rotate(_angle);

        int side = _tam/_scale;

        g.drawLine(-side,-side, side,-side);
        g.drawLine(side,-side,side, side);
        g.drawLine(side,side, -side,side);
        g.drawLine(-side,side, -side,-side);

        g.restore();
    }

    public Utils.Point getPosition(){ return _position; }
}
