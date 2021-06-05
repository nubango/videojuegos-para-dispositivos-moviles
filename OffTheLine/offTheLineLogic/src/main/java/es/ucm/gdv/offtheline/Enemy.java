package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Enemy {

    private Utils.Point _pInitPosition;
    private Utils.Point _pPosition;
    private Utils.Point _pPosExtrem1;   // Posicion de un extremo del segmento (real)
    private Utils.Point _pPosExtrem2;   // Posicion de un extremo del segmento (real)
    private Utils.Vector _vDir;         // Direccion en la que se traslada
    private Utils.Vector _pOffset;      // Vector de movimiento

    private int _length;                // Longitud del segmento
    private int _scale = 1;
    private double _angle = 0;          // 0 horizontal, 90 vertical
    private int _color = 0xFFFF1D03;

    private double _speed;          // Velocidad circular. Positiva al contrario del reloj y negativa a favor del reloj

    private double _timeStop;       // Tiempo que está parado el enemigo
    private double _velocity = 0;       // Velocidad de traslacion
    private double _maxRight = 0;       // Limite de movimiento por la derecha
    private double _maxLeft = 0;        // Limite de movimiento por la izquierda
    private double _maxUp = 0;          // Limite de movimiento por la derecha
    private double _maxDown = 0;        // Limite de movimiento por la izquierda
    private double _timeStopped = 0;

    Enemy(double x, double y, int length, double angle, double speed, Utils.Vector offset,
          double timeMoving, double timeStop){

        _pInitPosition = new Utils.Point(x, y);
        _pPosition = new Utils.Point(x, y);
        _vDir = new Utils.Vector(0, 0);

        _length = length/2;
        _angle = angle;
        _speed = speed;
        _pOffset = offset;
        _timeStop = timeStop;

        if(_pOffset != null) {

            _vDir.x = _pOffset.x;
            _vDir.y = _pOffset.y;
            _vDir.normalize();


            double moduleOffsetVector = _pOffset.module();

            if (timeMoving > 0) {
                _velocity = moduleOffsetVector / timeMoving;
            }
            double aux1, aux2;
            aux1 = x;
            aux2 = x + _pOffset.x;
            _maxLeft = Math.min(aux1, aux2);
            _maxRight = Math.max(aux1, aux2);

            aux1 = y;
            aux2 = y + _pOffset.y;
            _maxUp = Math.min(aux1, aux2);
            _maxDown = Math.max(aux1, aux2);

            //calculatePoints();
        }
    }

    void update(double deltaTime){

        //calculatePoints();
        // Rotacion del enemigo. Si _speed es 0 no rota
        _angle = (_angle + _speed * deltaTime) % 360;


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
        if(_pPosExtrem1 != null && _pPosExtrem2 != null) {
            g.setColor(0x0F1D1DF3);
            g.drawLine((int) _pPosExtrem1.x, (int) _pPosExtrem1.y, (int) _pPosExtrem2.x, (int) _pPosExtrem2.y);
        }

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

    private void calculatePoints(){

//        _pPosExtrem1 = new Utils.Point((_pPosition.x + _length)*Math.cos(_angle) - _pPosition.y *Math.sin(_angle),
//                (_pPosition.x + _length)*Math.sin(_angle) + _pPosition.y*Math.cos(_angle));
//        _pPosExtrem2 = new Utils.Point((_pPosition.x - _length)*Math.cos(_angle) - _pPosition.y *Math.sin(_angle),
//                (_pPosition.x - _length)*Math.sin(_angle) + _pPosition.y*Math.cos(_angle));


        if(_angle == 0) {
            _pPosExtrem1 = new Utils.Point(_pPosition.x + _length + OffTheLineLogic.LOGIC_WIDTH / 2, -_pPosition.y + OffTheLineLogic.LOGIC_HEIGHT / 2);
            _pPosExtrem2 = new Utils.Point(_pPosition.x - _length + OffTheLineLogic.LOGIC_WIDTH / 2, -_pPosition.y + OffTheLineLogic.LOGIC_HEIGHT / 2);
        }else{
            _pPosExtrem1 = new Utils.Point(_pPosition.x + (_length * Math.cos(_angle * (Math.PI/180))) + OffTheLineLogic.LOGIC_WIDTH / 2,
                    -_pPosition.y + (_length * Math.cos(_angle * (Math.PI / 180))) + OffTheLineLogic.LOGIC_HEIGHT / 2);
            _pPosExtrem2 = new Utils.Point(_pPosition.x + (_length * Math.cos((_angle * (Math.PI/180)) + Math.PI)) + OffTheLineLogic.LOGIC_WIDTH / 2,
                    -_pPosition.y + (_length * Math.sin((_angle * Math.PI/180)) + Math.PI) + OffTheLineLogic.LOGIC_HEIGHT / 2);
        }




//        double coseno = Math.cos(-_angle);
//
//        Utils.Vector vpa = new Utils.Vector(_pPosition.x - _length, _pPosition.y);
//        double modulo = vpa.module();
//        //double y1 = (2*coseno*vpa.y + Math.sqrt(4*(coseno*coseno * vpa.y*vpa.y - modulo*modulo * coseno + vpa.x*vpa.x)))/2;
//        double y1 = Math.sqrt(4*( - _length*_length * coseno + _length*_length))/2;
//        double x1 = Math.sqrt(_length*_length - y1*y1);
//        _pPosExtrem1 = new Utils.Point(x1, y1);
//
//        double y2 = -y1;
//        double x2 = -x1;
//        _pPosExtrem2 = new Utils.Point(x2, y2);
//
//        _pPosExtrem1.x += (int)_pPosition.x + OffTheLineLogic.LOGIC_WIDTH / 2;
//        _pPosExtrem1.y += -(int)_pPosition.y + OffTheLineLogic.LOGIC_HEIGHT / 2;
//
//        _pPosExtrem2.x += (int)_pPosition.x + OffTheLineLogic.LOGIC_WIDTH / 2;
//        _pPosExtrem2.y += -(int)_pPosition.y + OffTheLineLogic.LOGIC_HEIGHT / 2;

    }

    Utils.Point[] getExtremPoints(){
        //calculatePoints();
        return new Utils.Point[]{_pPosExtrem1,_pPosExtrem2};
    }

    void reset(){
        _pPosition.x = _pInitPosition.x;
        _pPosition.y = _pInitPosition.y;
        _timeStopped = 0;
    }
}
