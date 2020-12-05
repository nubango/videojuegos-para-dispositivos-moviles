package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Item {

    Utils.Point _position;
    int _tam = 4;           // Tamaño de los segmentos que forman el item
    int _scale = 1;
    int _color = 0xFFFFEF00;

    double _speed = 150;    // Velocidad de rotacion
    double _angle = 0;      // Angulo de giro

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
        if(!g.save()) {
            return;
        }

        g.translate((int)_position.x, (int)_position.y);
        g.scale(_scale, _scale);
        g.rotate(_angle);

        g.drawLine(-_tam,-_tam, _tam,-_tam);
        g.drawLine(_tam,-_tam,_tam, _tam);
        g.drawLine(_tam,_tam, -_tam,_tam);
        g.drawLine(-_tam,_tam, -_tam,-_tam);

        g.restore();
    }
}
