package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Enemy {

    Utils.Point _position;
    int _length = 5;        // Longitud del segmento
    int _scale = 1;
    double _angle = 0;      // 0 horizontal, 90 vertical
    int _color = 0xFFFF1D03;

    double _speed = 0;      // Velocidad circular. Positiva al contrario del reloj y negativa a favor del reloj
    Utils.Point _offset;     // Longitud de lo que se mueve hacia arriba y hacia abajo
    double _timeStop = 0;   // Tiempo que está parado el enemigo
    double _timeMoving = 0; // Tiempo que tarda en ir desde la posicion hasta el offset

    double _velocity = 0;   // Velocidad de traslacion
    double _maxRight = 0;   // Limite de movimiento por la derecha
    double _maxLeft = 0;    // Limite de movimiento por la izquierda
    double _maxUp = 0;   // Limite de movimiento por la derecha
    double _maxDown = 0;    // Limite de movimiento por la izquierda
    double _timeStopped = 0;

    Enemy(double x, double y, int length, double angle, double speed, Utils.Point offset, double timeStop, double timeMoving){
        _position = new Utils.Point(x, y);;
        _length = length/2;
        _angle = angle;
        _speed = speed;
        _offset = offset;
        _timeStop = timeStop;
        _timeMoving = timeMoving;

        if(_offset != null) {

            double xOffsetVector = _offset.x - _position.x;
            double yOffsetVector = _offset.y - _position.y;

            double moduleOffsetVector = Math.sqrt(xOffsetVector * xOffsetVector + yOffsetVector * yOffsetVector);

            if (timeMoving > 0) {
                _velocity = moduleOffsetVector / timeMoving;
            }
            _maxLeft = x;
            _maxRight = x + moduleOffsetVector;
            _maxUp = y;
            _maxDown = y + moduleOffsetVector;
        }
    }

    void update(double deltaTime){
        /*
        // Rotacion del enemigo. Si _speed es 0 no rota
        _angle = (_angle + _speed * deltaTime) % 360;

        // Traslacion del enemigo. Si _offset o _timeMoving son 0 no se mueve
        if(_timeStopped >= _timeStop) {
            _position.x += _velocity * deltaTime;
            while (_position.x < _maxLeft || _position.x > _maxRight) {
                // Vamos a pintar fuera del intervalo. Rectificamos e iniciamos la cuenta de parado
                _timeStopped = 0;
                if (_position.x < _maxLeft) {
                    // Nos salimos por la izquierda. Rebotamos.
                    _position.x = 2 * _maxLeft - _position.x;
                    _velocity *= -1;
                } else if (_position.x > _maxRight) {
                    // Nos salimos por la derecha. Rebotamos
                    _position.x = 2 * _maxRight - _position.x;
                    _velocity *= -1;
                }
            } // while

            _position.y += _velocity * deltaTime;
            while (_position.y < _maxUp || _position.y > _maxDown) {
                // Vamos a pintar fuera del intervalo. Rectificamos e iniciamos la cuenta de parado
                _timeStopped = 0;
                if (_position.y < _maxUp) {
                    // Nos salimos por la izquierda. Rebotamos.
                    _position.y = 2 * _maxUp - _position.y;
                    _velocity *= -1;
                } else if (_position.y > _maxDown) {
                    // Nos salimos por la derecha. Rebotamos
                    _position.y = 2 * _maxDown - _position.y;
                    _velocity *= -1;
                }
            } // while
        } // if (_timeStopped > _timeStop)
        else{
            _timeStopped += deltaTime;
        }
         */
    }

    void render(Graphics g) {
        g.setColor(_color);

        if(!g.save()) {
            return;
        }

        g.translate((int)_position.x, (int)_position.y);
        g.scale(_scale, _scale);
        g.rotate(_angle);

        g.drawLine(-_length,0, _length,0);

        g.restore();
    }

}
