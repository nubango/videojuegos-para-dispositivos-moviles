package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Item {

    private Utils.Point _position;
    private int _tam = 4;           // Tamaño de los segmentos que forman el item
    private int _scale = 1;
    private int _color = 0xFFFFEF00;

    private double _speed = 150;    // Velocidad de rotacion
    private double _angle = 0;      // Angulo de giro

    Item(double x, double y) {
        set_position(new Utils.Point(x, y));
    }

    void update(double deltaTime){
        set_angle((get_angle() + get_speed() * deltaTime) % 360);
    }

    void render(Graphics g) {
        g.setColor(get_color());
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

        g.translate((int) get_position().x, (int) get_position().y);
        g.scale(get_scale(), get_scale());
        g.rotate(get_angle());

        g.drawLine(-get_tam(),-get_tam(), get_tam(),-get_tam());
        g.drawLine(get_tam(),-get_tam(), get_tam(), get_tam());
        g.drawLine(get_tam(), get_tam(), -get_tam(), get_tam());
        g.drawLine(-get_tam(), get_tam(), -get_tam(),-get_tam());

        g.restore();
    }

    public Utils.Point get_position() {
        return _position;
    }

    public void set_position(Utils.Point _position) {
        this._position = _position;
    }

    public int get_tam() {
        return _tam;
    }

    public void set_tam(int _tam) {
        this._tam = _tam;
    }

    public int get_scale() {
        return _scale;
    }

    public void set_scale(int _scale) {
        this._scale = _scale;
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

    public double get_angle() {
        return _angle;
    }

    public void set_angle(double _angle) {
        this._angle = _angle;
    }
}
