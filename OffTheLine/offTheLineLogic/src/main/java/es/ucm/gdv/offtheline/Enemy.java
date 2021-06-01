package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Enemy {

    private Utils.Point _pPosition;
    private Utils.Point _pLastPosition;
    private Utils.Vector _vDir;         // Direccion en la que se traslada
    private int _orientation = 1;       // Orientacion normal o invertida

    private Utils.Point _pPosInit;      // Posicion inicial de movimiento
    private Utils.Point _pPosEnd;      // Posicion inicial de movimiento
    private Utils.Vector _pOffset;      // Vector de movimiento

    private int _length;                // Longitud del segmento
    private int _scale = 1;
    private double _angle = 0;          // 0 horizontal, 90 vertical
    private int _color = 0xFFFF1D03;

    private double _speed = 0;          // Velocidad circular. Positiva al contrario del reloj y negativa a favor del reloj
    private double _translateTime = 0;  // Tiempo actual que lleva trasladandose


    private double _timeMoving = 0;     // Tiempo que tarda en ir desde la posicion hasta el offset
    private double _timeStop = 0;       // Tiempo que está parado el enemigo
    private double _velocity = 0;       // Velocidad de traslacion
    private double _maxRight = 0;       // Limite de movimiento por la derecha
    private double _maxLeft = 0;        // Limite de movimiento por la izquierda
    private double _maxUp = 0;          // Limite de movimiento por la derecha
    private double _maxDown = 0;        // Limite de movimiento por la izquierda
    private double _timeStopped = 0;

    Enemy(double x, double y, int length, double angle, double speed, Utils.Vector offset,
          double timeMoving, double timeStop){
        _pPosition = new Utils.Point(x, y);
        _pLastPosition = new Utils.Point(x, y);
        _vDir = new Utils.Vector(0, 0);

        _length = length/2;
        _angle = angle;
        _speed = speed;
        _pOffset = offset;
        _timeStop = timeStop;
        _timeMoving = timeMoving;

        if(_pOffset != null) {

            _pPosInit = new Utils.Point(x, y);
            _pPosEnd = new Utils.Point(x + _pOffset.x, y + _pOffset.y);

            _vDir.x = _pOffset.x;
            _vDir.y = _pOffset.y;
            _vDir.normalize();


            double moduleOffsetVector = _pOffset.module();

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

        // Rotacion del enemigo. Si _speed es 0 no rota
        _angle = (_angle + _speed * deltaTime) % 360;

//        if(_translateTime <= _timeMoving) {
//
//            double percent = _translateTime / _timeMoving;
//            // Posicion relativa donde tiene que estar en funcion del tiempo que ha pasado
//            double x = percent * _pOffset.x;
//            double y = percent * _pOffset.y;
//
//            // Se la sumamos al punto inicial
//            x += _pPosInit.x;
//            y += _pPosInit.y;
//
//            _pLastPosition.x = _pPosition.x;
//            _pLastPosition.y = _pPosition.y;
//            _pPosition.x = x;
//            _pPosition.y = y;
//
////            // Hallamos distancia recorrida orig-position
////            Utils.Vector vDistRecor;
////            if (_orientation == 1)
////                vDistRecor = new Utils.Vector(_pPosition.x - _pPosInit.x, _pPosition.y - _pPosInit.y);
////            else
////                vDistRecor = new Utils.Vector(_pPosition.x - _pPosEnd.x, _pPosition.y - _pPosEnd.y);
////
////            // comprobando que el enemigo no se sale del segmento de movimiento
////            if (_pOffset.module() - vDistRecor.module() < 0) {
////                _orientation *= -1;
////                _vDir.x *= -1;
////                _vDir.y *= -1;
////            }
//        }
//        else
//            _orientation = -1;
//
//        if(_pPosition.x > _pPosInit.x + _pOffset.x || _pPosition.y > _pPosInit.y + _pOffset.y)
//            _orientation = -1;
//        else if(_pPosition.x < _pPosInit.x || _pPosition.y > _pPosInit.y)
//            _orientation = 1;
//
//
//        _translateTime += deltaTime*_orientation;


        if(_pOffset != null) {
            // Traslacion del enemigo. Si _offset o _timeMoving son 0 no se mueve
            if (_timeStopped >= _timeStop) {
                _pPosition.x += _velocity * _vDir.x * deltaTime;
                while (_pPosition.x < _maxLeft || _pPosition.x > _maxRight) {
                    // Vamos a pintar fuera del intervalo. Rectificamos e iniciamos la cuenta de parado
                    _timeStopped = 0;
                    if (_pPosition.x < _maxLeft) {
                        // Nos salimos por la izquierda. Rebotamos.
                        _pPosition.x = 2 * _maxLeft - _pPosition.x;
                        _velocity *= -1;
                    } else if (_pPosition.x > _maxRight) {
                        // Nos salimos por la derecha. Rebotamos
                        _pPosition.x = 2 * _maxRight - _pPosition.x;
                        _vDir.x *= -1;
                    }
                } // while

                _pPosition.y += _velocity* _vDir.y * deltaTime;
                while (_pPosition.y < _maxUp || _pPosition.y > _maxDown) {
                    // Vamos a pintar fuera del intervalo. Rectificamos e iniciamos la cuenta de parado
                    _timeStopped = 0;
                    if (_pPosition.y < _maxUp) {
                        // Nos salimos por la izquierda. Rebotamos.
                        _pPosition.y = 2 * _maxUp - _pPosition.y;
                        _velocity *= -1;
                    } else if (_pPosition.y > _maxDown) {
                        // Nos salimos por la derecha. Rebotamos
                        _pPosition.y = 2 * _maxDown - _pPosition.y;
                        _vDir.y *= -1;
                    }
                } // while
            } // if (_timeStopped > _timeStop)
            else {
                _timeStopped += deltaTime;
            }
        } // fin if(offset != null)

    }

    void render(Graphics g) {
        g.setColor(_color);

        if(!g.save()) {
            return;
        }

        g.translate((int) _pPosition.x + OffTheLineLogic.LOGIC_WIDTH / 2,-(int) _pPosition.y +
                OffTheLineLogic.LOGIC_HEIGHT / 2);
        g.scale(_scale, _scale);
        g.rotate(-_angle);

        g.drawLine(-_length,0, _length,0);

        g.restore();
    }
}
