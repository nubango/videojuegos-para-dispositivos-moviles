package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player {
    Utils.Point _position;
    Utils.Point _lastPosition;
    Utils.Point _originJumpPosition;
    int _tam = 6;           // Tamaño de los segmentos que forman el item
    int _scale = 1;
    int _lifes = 10;        // Vidas del jugador
    int _color = 0xFF0081F9;

    double _angle = 0;      // Angulo de rotacion
    double _speed = 150;    // Velocidad de rotacion

    double _velocity = 0;
    double _translateVelocity = 200;
    double _jumpVelocity = 1000;
    Utils.Point _direction;  // Velocidad de traslacion
    Utils.Point _vDest;    // Vertice destino, a donde voy
    int _dir = 1;

    Path _currentPath;
    int _currentLine = 0;

    boolean _jump = false;
    boolean _jumping = false;


    Player(double x, double y){
        _lastPosition = new Utils.Point(x, y);
        _position = new Utils.Point(x, y);
        _originJumpPosition = new Utils.Point(x, y);

         _direction = new Utils.Point(1,1);
         _vDest = new Utils.Point(x,y);

         _velocity = _translateVelocity;
    }

    void updatePathCoordinates(){

        int dest, orig;
        if(_dir < 0) {
            dest = _currentLine;
            orig = (_currentLine + 1) % _currentPath._vertexes.size();
        } else {
            orig = _currentLine;
            dest = (_currentLine + 1) % _currentPath._vertexes.size();
        }

        // Asignamos los valores
        double xOrigin = _currentPath._vertexes.get(orig).getX();
        double yOrigin = _currentPath._vertexes.get(orig).getY();

        _vDest.x = _currentPath._vertexes.get(dest).getX();
        _vDest.y = _currentPath._vertexes.get(dest).getY();

        _position.x = xOrigin;
        _position.y = yOrigin;

        double xDir = _vDest.x - xOrigin;
        double yDir = _vDest.y - yOrigin;

        // Normalizamos coordenadas del vector
        double length = Math.sqrt( xDir*xDir + yDir*yDir );
        if (length != 0) {
            xDir = xDir/length;
            yDir = yDir/length;
        }

        _direction.x = xDir;
        _direction.y = yDir;

    }

    void updateJumpCoordinates(){
        // actualizo los valores necerarios para que se mueva correctamente
        _originJumpPosition.x = _position.x;
        _originJumpPosition.y = _position.y;

        _direction.x = _currentPath._directions.get(_currentLine).getX();
        _direction.y = _currentPath._directions.get(_currentLine).getY();

        // cambio la direccion de traslacion
        _dir = -_dir;

        // velocidad de salto
        _velocity = _jumpVelocity;

    } // updateJumpCoordinates()

    Utils.Point checkCollision(){

        Utils.Point p = null;
        for (int i = 0; i < _currentPath._vertexes.size() && p == null; i++) {
            if (i != _currentLine) {
                double xori = _currentPath._vertexes.get(i).x;
                double yori = _currentPath._vertexes.get(i).y;
                double xdest = _currentPath._vertexes.get((i + 1) % _currentPath._vertexes.size()).x;
                double ydest = _currentPath._vertexes.get((i + 1) % _currentPath._vertexes.size()).y;

                p = Utils.segmentsIntersection(_lastPosition.x, _lastPosition.y, _position.x, _position.y, xori, yori, xdest, ydest);

                if (p != null) {
                    _currentLine = i;
                }
            }
        }

        return p;
    }


    void setCurrentPath(Path path){
        _currentPath = path;

        updatePathCoordinates();

    }

    void handleInput(List<Input.TouchEvent> events) {
        if(events == null)
            return;

        for (Input.TouchEvent e:events) {
            if(!_jumping&&e._type == Input.Type.press){
                _jump = true;
            }
        }
    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;

        // Traslacion del player
        _lastPosition.x = _position.getX();
        _lastPosition.y = _position.getY();
        _position.x += _direction.x * _velocity * deltaTime;
        _position.y += _direction.y * _velocity * deltaTime;


        if(_jump){
            _jump = false;
            _jumping = true;
            updateJumpCoordinates();
        }
        else if(_jumping) {

            Utils.Point p = checkCollision();
            // comprobar colision con linea
            //if (xPosition > xDestination || yPosition > yDestination) {
            if (p != null) {
                _jumping = false;
                _velocity = _translateVelocity;

                updatePathCoordinates();

                _position.x = (int)p.x;
                _position.y = (int)p.y;
            }
        }
        else if(!_jumping) {

            // Multiplicamos los valores por la direccion para poder compararlos
            double xPosition = _position.x * _direction.x;
            double yPosition = _position.y * _direction.y;
            double xDestination = _vDest.x * _direction.x;
            double yDestination = _vDest.y * _direction.y;

            // Si el player se sale del camino marcado por _vOrigin-_vDest
            if (xPosition > xDestination || yPosition > yDestination) {

                _currentLine += _dir;
                if(_currentLine < 0)
                    _currentLine = _currentPath._vertexes.size() - 1;
                else if(_currentLine >= _currentPath._vertexes.size())
                    _currentLine %= _currentPath._vertexes.size();

                updatePathCoordinates();
            }
        }

    }

    void render(Graphics g){
        g.setColor(_color);

        if(!g.save()) {
            return;
        }

        // pintamos la linea al saltar
        if(_jumping)
            g.drawLine((int)_position.x, (int)_position.y, (int)_originJumpPosition.x, (int)_originJumpPosition.y);

        g.translate((int)_position.x, (int)_position.y);
        g.scale(_scale, _scale);
        g.rotate(_angle);



        g.drawLine(-_tam,-_tam, _tam,-_tam);
        g.drawLine(_tam,-_tam,_tam, _tam);
        g.drawLine(_tam,_tam, -_tam,_tam);
        g.drawLine(-_tam,_tam, -_tam,-_tam);

        g.restore();
    }
}
