package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player {
    double _x = 0, _y = 0;
    int _tam = 5;           // Tamaño de los segmentos que forman el item
    int _scale = 4;
    int _lifes = 10;        // Vidas del jugador
    int _color = 0xFF0081F9;

    double _angle = 0;      // Ángulo de rotacion
    double _speed = 150;    // Velocidad de rotacion

    int _direction = 0;     // Direccion en la que se mueve
    double _velocity = 0;   // Velocidad de traslacion



    Player(int x, int y){
        _x = x;
        _y = y;
    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;
    }

    void render(Graphics g){
        g.setColor(_color);

        if(g.save()) {
            g.translate((int)_x,(int)_y);
            g.scale(_scale, _scale);
            g.rotate(_angle);
        }


        g.drawLine(-_tam,-_tam, _tam,-_tam);
        g.drawLine(_tam,-_tam,_tam, _tam );
        g.drawLine(_tam,_tam, -_tam,_tam);
        g.drawLine(-_tam,_tam, -_tam,-_tam);

        g.restore();
    }
}
