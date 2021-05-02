package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player {
    private Utils.Point _pPosition;
    private Utils.Point _lastPosition;
    private Utils.Point _originJumpPosition;

    private int _tam = 6;           // Tamaño de los segmentos que forman el item
    private int _scale = 1;
    private int _lifes = 10;        // Vidas del jugador
    private int _color = 0xFF0081F9;

    private double _angle = 0;      // Angulo de rotacion
    private double _speed = 150;    // Velocidad de rotacion

    private double _velocity = 0;

    private double _jumpVelocity = 1000;
    private Utils.Point _direction;  // Velocidad de traslacion

    private int _dir = 1;

    private int _currentPathIndex = 0;

    private boolean _jump = false;
    private boolean _jumping = false;


// Nuevo-----------------------------------------------
    private Utils.Point _pOrig;                 // Punto origen
    private Utils.Point _pDest;                 // Punto destino
    private Utils.Point _vCurSeg;               // Vector segmento
    private Utils.Point _vDir;                  // Vector direccion

    private double _translateVelocity = 200;    // Velocidad de traslacion del player

    private Path _currentPath = null;                  // Trazo en el que está el player
    private int _currentLine = -1;               // Id de la linea en la que esta el player


    Player(){
        _lastPosition = new Utils.Point(0, 0);
        _pPosition = new Utils.Point(0, 0);
        _originJumpPosition = new Utils.Point(0, 0);

        _direction = new Utils.Point(1,1);

        _pOrig = new Utils.Point(0,0);
        _pDest = new Utils.Point(0,0);
        _vCurSeg = new Utils.Point(0,0);
        _vDir = new Utils.Point(0,0);

        _velocity = _translateVelocity;
    }

//    void updatePathCoordinates(){
//        int destId, origId;
//        if(_dir < 0) {
//            destId = _currentLine;
//            origId = (_currentLine + 1) % _currentPath.getVertexes().size();
//        } else {
//            origId = _currentLine;
//            destId = (_currentLine + 1) % _currentPath.getVertexes().size();
//        }
//
//        // Asignamos los valores
//        double xOrig = _currentPath.getVertexes().get(origId).getX();
//        double yOrig = _currentPath.getVertexes().get(origId).getY();
//
//        _vDest.x = _currentPath.getVertexes().get(destId).getX();
//        _vDest.y = _currentPath.getVertexes().get(destId).getY();
//
//        _position.x = xOrig;
//        _position.y = yOrig;
//
//        double xDir = _vDest.x - xOrig;
//        double yDir = _vDest.y - yOrig;
//
//        // Normalizamos coordenadas del vector
//        double length = Math.sqrt( xDir*xDir + yDir*yDir );
//        if (length != 0) {
//            xDir = xDir/length;
//            yDir = yDir/length;
//        }
//
//        _direction.x = xDir;
//        _direction.y = yDir;
//
//    }
//
//    void updateJumpCoordinates(){
//        // actualizo los valores necerarios para que se mueva correctamente
//        _originJumpPosition.x = _position.x;
//        _originJumpPosition.y = _position.y;
//
//        _direction.x = _currentPath.getDirections().get(_currentLine).getX();
//        _direction.y = _currentPath.getDirections().get(_currentLine).getY();
//
//        // cambio la direccion de traslacion
//        _dir = -_dir;
//
//        // velocidad de salto
//        _velocity = _jumpVelocity;
//
//    } // updateJumpCoordinates()
//
//    /*
//    Utils.Point checkCollision() {
//
//        Utils.Point p = null;
//        for (int j = 0; j < currentLevel._paths.size(); j++) {
//            for (int i = 0; i < currentLevel._paths.get(j)._vertexes.size() && p == null; i++) {
//                if (_currentPathIndex == j && _currentLine == i) {
//                    continue;
//                }
//                double xori = _currentPath._vertexes.get(i).x;
//                double yori = _currentPath._vertexes.get(i).y;
//                double xdest = _currentPath._vertexes.get((i + 1) % _currentPath._vertexes.size()).x;
//                double ydest = _currentPath._vertexes.get((i + 1) % _currentPath._vertexes.size()).y;
//                p = Utils.segmentsIntersection(_lastPosition.x, _lastPosition.y, _position.x, _position.y, xori, yori, xdest, ydest);
//                _currentPathIndex = j;
//                setCurrentPath(currentLevel._paths.get(j));
//                if (p != null) {
//                    _currentLine = i;
//                }
//            }
//        }
//        return p;
//    }
//     */
//
//    Utils.Point checkCollision(){
//
//        Utils.Point p = null;
//        for (int i = 0; i < _currentPath.getVertexes().size() && p == null; i++) {
//            if (i != _currentLine) {
//                double xori = _currentPath.getVertexes().get(i).x;
//                double yori = _currentPath.getVertexes().get(i).y;
//                double xdest = _currentPath.getVertexes().get((i + 1) % _currentPath.getVertexes().size()).x;
//                double ydest = _currentPath.getVertexes().get((i + 1) % _currentPath.getVertexes().size()).y;
//
//                p = Utils.segmentsIntersection(_lastPosition.x, _lastPosition.y, _position.x, _position.y, xori, yori, xdest, ydest);
//
//                if (p != null) {
//                    _currentLine = i;
//                }
//            }
//        }
//
//        return p;
//    }

    /**
     * Actualiza el segmento en el que se mueve el player
     * */
    private void updateCurSeg(){
        _currentLine = (_currentLine + 1) % _currentPath.getVertexes().size();
        // Calculamos los ids de los puntos origen y destino del siguiente segmento a recorrer
        int destId, origId;
        origId = _currentLine;
        destId = (_currentLine + 1) % _currentPath.getVertexes().size();

        // Creamos los puntos origen y destino del primer segmento del trazo
        double xOrig = _currentPath.getVertexes().get(origId).x;
        double yOrig = _currentPath.getVertexes().get(origId).y;
        _pOrig = new Utils.Point(xOrig, yOrig);

        double xDest = _currentPath.getVertexes().get(destId).x;
        double yDest = _currentPath.getVertexes().get(destId).y;
        _pDest = new Utils.Point(xDest, yDest);


        // Creamos el vector del segmento
        _vCurSeg = new Utils.Point(_pDest.x - _pOrig.x, _pDest.y - _pOrig.y);

        // Creamos y normalizamos el vector direccion
        _vDir = new Utils.Point(_vCurSeg.x, _vCurSeg.y);
        double mag = _vCurSeg.magnitud();
        _vDir.x /= mag;
        _vDir.y /= mag;

        _pPosition.x = _pOrig.x;
        _pPosition.y = _pOrig.y;
    }

    void setCurrentPath(Path path){
        _currentPath = path;
        updateCurSeg();
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

        _pPosition.x += _vDir.x*_translateVelocity*deltaTime;
        _pPosition.y += _vDir.y*_translateVelocity*deltaTime;

        // hallamos distancia recorrida orig-position
        Utils.Point vOrigPos = new Utils.Point(_pPosition.x - _pOrig.x, _pPosition.y - _pOrig.y);

        // se la restamos las magnitudes de los vectores _vCurSeg y vOrigPos
        if(_vCurSeg.magnitud() - vOrigPos.magnitud() < 0)
            // si es negativa cambiamos de segmanto
            updateCurSeg();


//        // Traslacion del player
//        _lastPosition.x = _position.getX();
//        _lastPosition.y = _position.getY();
//        _position.x += _direction.x * _velocity * deltaTime;
//        _position.y += _direction.y * _velocity * deltaTime;
//
//
//        if(_jump){
//            _jump = false;
//            _jumping = true;
//            updateJumpCoordinates();
//        }
//        else if(_jumping) {
//
//            Utils.Point p = checkCollision();
//            // comprobar colision con linea
//            //if (xPosition > xDestination || yPosition > yDestination) {
//            if (p != null) {
//                _jumping = false;
//                _velocity = _translateVelocity;
//
//                updatePathCoordinates();
//
//                _position.x = (int)p.x;
//                _position.y = (int)p.y;
//            }
//        }
//        else if(!_jumping) {
//
//            // Multiplicamos los valores por la direccion para poder compararlos
//            double xPosition = _position.x * _direction.x;
//            double yPosition = _position.y * _direction.y;
//            double xDestination = _vDest.x * _direction.x;
//            double yDestination = _vDest.y * _direction.y;
//
//            // Si el player se sale del camino marcado por _vOrigin-_vDest
//            if (xPosition > xDestination || yPosition > yDestination) {
//
//                _currentLine = _currentLine + _dir;
//                if(_currentLine < 0)
//                    _currentLine = _currentPath.getVertexes().size() - 1;
//                else if(_currentLine >= _currentPath.getVertexes().size())
//                    _currentLine = _currentLine % _currentPath.getVertexes().size();
//
//                updatePathCoordinates();
//            }
//        }
    }

    void render(Graphics g){
        g.setColor(_color);

        if(!g.save()) {
            return;
        }

        g.translate(_pPosition.x + OffTheLineLogic.LOGIC_WIDTH / 2, -_pPosition.y +
                OffTheLineLogic.LOGIC_HEIGHT / 2);

        g.scale(_scale, _scale);
        g.rotate(_angle);

        int side = _tam/_scale;

        g.drawLine(-side, -side, side, -side);
        g.drawLine(side, -side, side, side);
        g.drawLine(side, side, -side, side);
        g.drawLine(-side, side, -side, -side);

        g.restore();

        jumpLineDebug(g);
    }

    void jumpLineDebug(Graphics g){
        // DEBUG
        if(!g.save()) {
            return;
        }
        g.translate((OffTheLineLogic.LOGIC_WIDTH / 2),OffTheLineLogic.LOGIC_HEIGHT / 2);
        g.scale(_scale, -_scale);

        if(_jumping)
            g.drawLine((int) _pPosition.x/_scale, (int) _pPosition.y/_scale,
                    (int)_originJumpPosition.x/_scale, (int)_originJumpPosition.y/_scale);

        g.restore();
        // DEBUG
    }
}
