package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Item {

    private Utils.Point _position;
    private int _initTam = 4;           // Tamaño inicial de los segmentos que forman el item
    private int _initScale = 2;
    private int _tam = 4;           // Tamaño actual de los segmentos que forman el item
    private int _scale = 2;
    private int _color = 0xFFFFEF00;

    private double _speed = 150;    // Velocidad de rotacion
    private double _angle = 0;      // Angulo de giro

    private boolean _taken = false;
    private boolean _isAnimated = false;
    private boolean _animationStart = false;

    private int _maxTamAnim = 15;
    private double _tamAnim;
    private double _velocityAnim = 75;

    private double _timeAnim = 0.4;
    private double _elapsedTime = 0;

    private Level _currentLevel = null;

    Item(double x, double y) {
        _position = new Utils.Point(x, y);
        _tamAnim = _tam;
    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;
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
        if(!g.save())
            return;
        g.translate((int)_position.x + OffTheLineLogic.LOGIC_WIDTH / 2,-(int)_position.y +
                OffTheLineLogic.LOGIC_HEIGHT / 2);
        g.scale(_scale, _scale);
        g.rotate(_angle);

        int side = _tam/_scale;

        g.drawLine(-side,-side, side,-side);
        g.drawLine(side,-side,side, side);
        g.drawLine(side,side, -side,side);
        g.drawLine(-side,side, -side,-side);

        g.restore();
    }

    Utils.Point getPosition(){ return _position; }

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
}
















