package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player {
    Utils.Point _position;
    int _tam = 12;           // Tamaño de los segmentos que forman el item
    int _scale = 1;
    int _lifes = 10;        // Vidas del jugador
    int _color = 0xFF0081F9;

    double _angle = 0;      // Angulo de rotacion
    double _speed = 150;    // Velocidad de rotacion

    int _direction = 0;     // Direccion en la que se mueve
    double _velocity = 0;   // Velocidad de traslacion
    double _maxRight = 0;   // Limite de movimiento por la derecha
    double _maxLeft = 0;    // Limite de movimiento por la izquierda

    Path _currentPath;



    Player(double x, double y){
        _position = new Utils.Point(x, y);

    }

    void setCurrentPath(Path path){ _currentPath = path; }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;

        // Traslacion del enemigo
       /* _position.x += _velocity * deltaTime;
        while (_position.x < _maxLeft || _position.x > _maxRight) {
            // Vamos a pintar fuera del intervalo. Rectificamos
            if (_position.x < _maxLeft) {
                // Nos salimos por la izquierda. Rebotamos.
                _position.x = _maxLeft;
                _velocity *= -1;
            } else if (_position.x > _maxRight) {
                // Nos salimos por la derecha. Rebotamos
                _position.x = 2 * _maxRight - _position.x;
                _velocity *= -1;
            }
        } // while

        */
    }

    void render(Graphics g){
        g.setColor(_color);

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
    }
}
