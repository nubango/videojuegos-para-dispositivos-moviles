package es.ucm.gdv.offtheline;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.List;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player {
    private Utils.Point _position;
    private Utils.Point _lastPosition;
    private Utils.Point _originJumpPosition;

    private int _tam = 6;           // Tamaño de los segmentos que forman el item
    private int _scale = 2;
    private int _lifes = 10;        // Vidas del jugador
    private int _color = 0xFF0081F9;

    private double _angle = 0;      // Angulo de rotacion
    private double _speed = 150;    // Velocidad de rotacion

    private double _velocity = 0;
    private double _translateVelocity = 200;
    private double _jumpVelocity = 1000;
    private Utils.Point _direction;  // Velocidad de traslacion
    private Utils.Point _vDest;    // Vertice destino, a donde voy
    private int _dir = 1;

    private Path _currentPath;
    private int _currentPathIndex = 0;
    private int _currentLine = 0;

    private boolean _jump = false;
    private boolean _jumping = false;

    Player(){
        _lastPosition = new Utils.Point(0, 0);
        _position = new Utils.Point(0, 0);
        _originJumpPosition = new Utils.Point(0, 0);

         _direction = new Utils.Point(1,1);
         _vDest = new Utils.Point(0,0);

         _velocity = _translateVelocity;
    }

    void updatePathCoordinates(){

        int dest, orig;
        if(_dir < 0) {
            dest = _currentLine;
            orig = (_currentLine + 1) % _currentPath.getVertexes().size();
        } else {
            orig = _currentLine;
            dest = (_currentLine + 1) % _currentPath.getVertexes().size();
        }

        // Asignamos los valores
        double xOrigin = _currentPath.getVertexes().get(orig).getX();
        double yOrigin = _currentPath.getVertexes().get(orig).getY();

        _vDest.x = _currentPath.getVertexes().get(dest).getX();
        _vDest.y = _currentPath.getVertexes().get(dest).getY();

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

        _direction.x = _currentPath.getDirections().get(_currentLine).getX();
        _direction.y = _currentPath.getDirections().get(_currentLine).getY();

        // cambio la direccion de traslacion
        _dir = -_dir;

        // velocidad de salto
        _velocity = _jumpVelocity;

    } // updateJumpCoordinates()

    /*
    Utils.Point checkCollision() {

        Utils.Point p = null;
        for (int j = 0; j < currentLevel._paths.size(); j++) {
            for (int i = 0; i < currentLevel._paths.get(j)._vertexes.size() && p == null; i++) {
                if (_currentPathIndex == j && _currentLine == i) {
                    continue;
                }
                double xori = _currentPath._vertexes.get(i).x;
                double yori = _currentPath._vertexes.get(i).y;
                double xdest = _currentPath._vertexes.get((i + 1) % _currentPath._vertexes.size()).x;
                double ydest = _currentPath._vertexes.get((i + 1) % _currentPath._vertexes.size()).y;
                p = Utils.segmentsIntersection(_lastPosition.x, _lastPosition.y, _position.x, _position.y, xori, yori, xdest, ydest);
                _currentPathIndex = j;
                setCurrentPath(currentLevel._paths.get(j));
                if (p != null) {
                    _currentLine = i;
                }
            }
        }
        return p;
    }
     */

    Utils.Point checkCollision(){

        Utils.Point p = null;
        for (int i = 0; i < _currentPath.getVertexes().size() && p == null; i++) {
            if (i != _currentLine) {
                double xori = _currentPath.getVertexes().get(i).x;
                double yori = _currentPath.getVertexes().get(i).y;
                double xdest = _currentPath.getVertexes().get((i + 1) % _currentPath.getVertexes().size()).x;
                double ydest = _currentPath.getVertexes().get((i + 1) % _currentPath.getVertexes().size()).y;

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
            if(!_jumping &&e._type == Input.Type.press){
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

                _currentLine = _currentLine + _dir;
                if(_currentLine < 0)
                    _currentLine = _currentPath.getVertexes().size() - 1;
                else if(_currentLine >= _currentPath.getVertexes().size())
                    _currentLine = _currentLine % _currentPath.getVertexes().size();

                updatePathCoordinates();
            }
        }
    }

    void render(Graphics g){
        g.setColor(_color);

        if(!g.save()) {
            return;
        }

        g.translate(_position.x + OffTheLineLogic.LOGIC_WIDTH / 2, _position.y +
                OffTheLineLogic.LOGIC_HEIGHT / 2);

        g.scale(_scale, _scale);
        g.rotate(_angle);

        g.drawLine(-_tam/_scale,-_tam/_scale, _tam/_scale,-_tam/_scale);
        g.drawLine(_tam/_scale,-_tam/_scale,_tam/_scale, _tam/_scale);
        g.drawLine(_tam/_scale,_tam/_scale, -_tam/_scale,_tam/_scale);
        g.drawLine(-_tam/_scale,_tam/_scale, -_tam/_scale,-_tam/_scale);

        g.restore();

        jumpLineDebug(g);
    }

    void jumpLineDebug(Graphics g){
        // DEBUG
        if(!g.save()) {
            return;
        }
        g.translate((OffTheLineLogic.LOGIC_WIDTH / 2),OffTheLineLogic.LOGIC_HEIGHT / 2);
        g.scale(_scale, _scale);

        if(_jumping)
            g.drawLine((int)_position.x/_scale, (int)_position.y/_scale,
                    (int)_originJumpPosition.x/_scale, (int)_originJumpPosition.y/_scale);

        g.restore();
        // DEBUG
    }
}
