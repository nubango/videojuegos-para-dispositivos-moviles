package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Enemy {

    private Utils.Point _position;
    private int _length = 5;        // Longitud del segmento
    private int _scale = 1;
    private double _angle = 0;      // 0 horizontal, 90 vertical
    private int _color = 0xFFFF1D03;

    private double _speed = 0;      // Velocidad circular. Positiva al contrario del reloj y negativa a favor del reloj
    private Utils.Point _offset;     // Longitud de lo que se mueve hacia arriba y hacia abajo
    private double _timeStop = 0;   // Tiempo que está parado el enemigo
    private double _timeMoving = 0; // Tiempo que tarda en ir desde la posicion hasta el offset

    private double _velocity = 0;   // Velocidad de traslacion
    private double _maxRight = 0;   // Limite de movimiento por la derecha
    private double _maxLeft = 0;    // Limite de movimiento por la izquierda
    private double _maxUp = 0;   // Limite de movimiento por la derecha
    private double _maxDown = 0;    // Limite de movimiento por la izquierda
    private double _timeStopped = 0;

    Enemy(double x, double y, int length, double angle, double speed, Utils.Point offset, double timeStop, double timeMoving){
        set_position(new Utils.Point(x, y));;
        set_length(length/2);
        set_angle(angle);
        set_speed(speed);
        set_offset(offset);
        set_timeStop(timeStop);
        set_timeMoving(timeMoving);

        if(get_offset() != null) {

            double xOffsetVector = get_offset().x - get_position().x;
            double yOffsetVector = get_offset().y - get_position().y;

            double moduleOffsetVector = Math.sqrt(xOffsetVector * xOffsetVector + yOffsetVector * yOffsetVector);

            if (timeMoving > 0) {
                set_velocity(moduleOffsetVector / timeMoving);
            }
            set_maxLeft(x);
            set_maxRight(x + moduleOffsetVector);
            set_maxUp(y);
            set_maxDown(y + moduleOffsetVector);
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
        g.setColor(get_color());

        if(!g.save()) {
            return;
        }

        g.translate((int) get_position().x, (int) get_position().y);
        g.scale(get_scale(), get_scale());
        g.rotate(get_angle());

        g.drawLine(-get_length(),0, get_length(),0);

        g.restore();
    }

    public Utils.Point get_position() {
        return _position;
    }

    public void set_position(Utils.Point _position) {
        this._position = _position;
    }

    public int get_length() {
        return _length;
    }

    public void set_length(int _length) {
        this._length = _length;
    }

    public int get_scale() {
        return _scale;
    }

    public void set_scale(int _scale) {
        this._scale = _scale;
    }

    public double get_angle() {
        return _angle;
    }

    public void set_angle(double _angle) {
        this._angle = _angle;
    }

    public int get_color() {
        return _color;
    }

    public void set_color(int _color) {
        this._color = _color;
    }

    public double get_speed() {
        return _speed;
    }

    public void set_speed(double _speed) {
        this._speed = _speed;
    }

    public Utils.Point get_offset() {
        return _offset;
    }

    public void set_offset(Utils.Point _offset) {
        this._offset = _offset;
    }

    public double get_timeStop() {
        return _timeStop;
    }

    public void set_timeStop(double _timeStop) {
        this._timeStop = _timeStop;
    }

    public double get_timeMoving() {
        return _timeMoving;
    }

    public void set_timeMoving(double _timeMoving) {
        this._timeMoving = _timeMoving;
    }

    public double get_velocity() {
        return _velocity;
    }

    public void set_velocity(double _velocity) {
        this._velocity = _velocity;
    }

    public double get_maxRight() {
        return _maxRight;
    }

    public void set_maxRight(double _maxRight) {
        this._maxRight = _maxRight;
    }

    public double get_maxLeft() {
        return _maxLeft;
    }

    public void set_maxLeft(double _maxLeft) {
        this._maxLeft = _maxLeft;
    }

    public double get_maxUp() {
        return _maxUp;
    }

    public void set_maxUp(double _maxUp) {
        this._maxUp = _maxUp;
    }

    public double get_maxDown() {
        return _maxDown;
    }

    public void set_maxDown(double _maxDown) {
        this._maxDown = _maxDown;
    }

    public double get_timeStopped() {
        return _timeStopped;
    }

    public void set_timeStopped(double _timeStopped) {
        this._timeStopped = _timeStopped;
    }
}
