package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Enemy {

    double _x = 0, _y = 0;
    int _length = 5;        // Longitud del segmento
    int _scale = 4;
    double _angle = 0;      // 0 horizontal, 90 vertical
    int _color = 0xFFFF1D03;

    double _speed = 0;      // Velocidad circular. Positiva al contrario del reloj y negativa a favor del reloj
    double _offset = 0;     // Longitud de lo que se mueve hacia arriba y hacia abajo
    double _timeStop = 0;   // Tiempo que está parado el enemigo
    double _timeMoving = 0; // Tiempo que tarda en ir desde la posicion hasta el offset


    double _velocity = 0;   // Velocidad de traslacion
    double _maxRight = 0;   // Límite de movimiento por la derecha
    double _maxLeft = 0;    // Límite de movimiento por la izquierda
    double _timeStopped = 0;

    Enemy(double x, double y, int length, double angle, double speed, double offset, double timeStop, double timeMoving){
        _x = x;
        _y = y;
        _length = length;
        _angle = angle;
        _speed = speed;
        _offset = offset;
        _timeStop = timeStop;
        _timeMoving = _timeMoving;

        if(timeMoving > 0)
            _velocity = _offset/timeMoving;
        _maxLeft = _x ;
        _maxRight = _x + _offset;

    }

    void update(double deltaTime){
        // Rotacion del enemigo. Si _speed es 0 no rota
        _angle = (_angle + _speed * deltaTime) % 360;

        // Traslacion del enemigo. Si _offset o _timeMoving son 0 no se mueve
        if(_timeStopped >= _timeStop) {
            _x += _velocity * deltaTime;
            while (_x < _maxLeft || _x > _maxRight) {
                // Vamos a pintar fuera del intervalo. Rectificamos e iniciamos la cuenta de parado
                _timeStopped = 0;
                if (_x < _maxLeft) {
                    // Nos salimos por la izquierda. Rebotamos.
                    _x = 2 * _maxLeft - _x;
                    _velocity *= -1;
                } else if (_x > _maxRight) {
                    // Nos salimos por la derecha. Rebotamos
                    _x = 2 * _maxRight - _x;
                    _velocity *= -1;
                }
            } // while
        } // if (_timeStopped > _timeStop)
        else{
            _timeStopped += deltaTime;
        }

    }

    void render(Graphics g) {
        g.setColor(_color);

        if(g.save()) {
            g.translate((int)_x,(int)_y);
            g.scale(_scale, _scale);
            g.rotate(_angle);
        }


        g.drawLine(-_length/2,0, _length/2,0);


        g.restore();
    }

}
