package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Item {

    double _angle = 0;
    int _x = 0, _y = 0;
    int _color = 0xFFFFEF00;
    int _tam = 5;
    int _scale = 4;
    double _speed = 10;

    Item(int x, int y) {
        _x = x;
        _y = y;
    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;
        System.out.println("Angulo de giro: " + _angle);
    }

    void render(Graphics g) {
        g.setColor(_color);

        if(g.save()) {
            g.translate(_x,_y);
            g.scale(_scale, _scale);
            g.rotate(Math.toRadians(_angle));
        }


        g.drawLine(-_tam,-_tam, _tam,-_tam);
        g.drawLine(_tam,-_tam,_tam, _tam );
        g.drawLine(_tam,_tam, -_tam,_tam);
        g.drawLine(-_tam,_tam, -_tam,-_tam);

        g.restore();
    }
}
