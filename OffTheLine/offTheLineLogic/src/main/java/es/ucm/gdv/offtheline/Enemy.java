package es.ucm.gdv.offtheline;

import com.sun.nio.file.SensitivityWatchEventModifier;

import es.ucm.gdv.engine.Graphics;

public class Enemy {

    int _x = 0, _y = 0;
    int _length = 5;        // Longitud del segmento
    double _angle = 0;      // 0 horizontal, 90 vertical
    double _speed = 0;      // Velocidad circular. Positiva al contrario del reloj y negativa a favor del reloj
    double _offset = 0;     // Longitud de lo que se mueve hacia arriba y hacia abajo
    double _timeStop = 0;   // Tiempo que está parado el enemigo
    double _timeMoving = 0; // Tiempo que tarda en ir desde la posicion hasta el offset
    int _color = 0xFFFF1D03;
    int _scale = 4;

    Enemy(int x, int y, int length, double angle, double speed, double offset, double time1, double time2){
        _x = x;
        _y = y;
        _length = length;
        _angle = angle;
        _speed = speed;
        _offset = offset;
        _timeStop = time1;
        _timeMoving = time2;
    }

    void update(double deltaTime){

    }

    void render(Graphics g) {
        g.setColor(_color);

        if(g.save()) {
            g.translate(_x,_y);
            g.scale(_scale, _scale);
            g.rotate(_angle);
        }


        g.drawLine(-_length/2,0, _length/2,0);


        g.restore();
    }

}
