package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Item {

    private Utils.Point _position;
    private int _initTam = 4;           // Tamaño inicial de los segmentos que forman el item
    private int _initScale = 2;
    private int _tam = 4;           // Mitad del tamaño actual los segmentos que forman el item
    private int _scale = 2;
    private int _color = 0xFFFFEF00;

    private double _rotationSpeed = 180;    // Velocidad de rotacion sobre sí mismo
    private double _currentAngle = 0;      // Angulo de giro

    private double _radius = 0;    // radio de rotacion en el plano
    private double _speed = 0;    // Velocidad de rotacion en el plano
    private double _angle = 0;      // Angulo de posicion

    private boolean _taken = false;
    private boolean _isAnimated = false;
    private boolean _animationStart = false;

    private int _maxTamAnim = 15;
    private double _tamAnim;
    private double _velocityAnim = 75;

    private double _timeAnim = 0.4;
    private double _elapsedTime = 0;

    private Level _currentLevel = null;

    Item(double x, double y, double radius, double speed, double angle) {
        _position = new Utils.Point(x, y);
        _tamAnim = _tam;
        _radius = radius;
        _speed = -speed;
        _angle = angle;
    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;
        _currentAngle = (_currentAngle + _rotationSpeed * deltaTime) % 360;
        if(_animationStart){
            if(_tam <= _maxTamAnim){
                _tamAnim += _velocityAnim*deltaTime;
                _tam = (int)_tamAnim;
                _scale = _tam/2;
            }
            else
                _animationStart = false;
        }

        if(_isAnimated){
            if(_elapsedTime > _timeAnim){
                _isAnimated = false;
                _elapsedTime = 0;
                _tam = _initTam;
                _scale = _initScale;
            }
            _elapsedTime += deltaTime;
        }
    }

    void render(Graphics g) {
        g.setColor(_color);

        if(!g.save()) {
            return;
        }
        g.translate((int)_position.x + OffTheLineLogic.LOGIC_WIDTH / 2,-(int)_position.y +
                OffTheLineLogic.LOGIC_HEIGHT / 2);
        g.rotate(_angle);
        g.translate(_radius, 0);
        g.scale(_scale, _scale);
        g.rotate(_currentAngle);

        int side = _tam/_scale;

        g.drawLine(-side,-side, side,-side);
        g.drawLine(side,-side,side, side);
        g.drawLine(side,side, -side,side);
        g.drawLine(-side,side, -side,-side);

        g.restore();
    }

    Utils.Point getPosition(){
        return _position;
    }

    void takeItem(Level l) {
        if(!_taken){
            _taken = true;
            _animationStart = true;
            _isAnimated = true;
            l.takeItem();
        }
    }

    boolean isTaken(){ return _taken; }
    boolean isAnimated(){ return _isAnimated; }

    void reset(){
        _taken = false;
        _isAnimated = false;
        _animationStart = false;
        _scale = _initScale;
        _tam = _initTam;
        _currentAngle = 0;
    }
}
















