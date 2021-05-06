package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player {

    private int _tam = 6;           // Tamaño de los segmentos que forman el item
    private int _lifes = 10;        // Vidas del jugador





// Nuevo-----------------------------------------------
    private Utils.Point _pPosition;             // Posicion actual
    private Utils.Point _pLastPosition;         // Posicion anterior

    private int _color = 0xFF0081F9;            // Color del player
    private int _scale = 1;                     // Escala del player

    private double _velocity = 0;               // Velocidad actual
    private double _translateVelocity = 200;    // Velocidad de traslacion del player
    private int _orientation = -1;                       // Sentido de movimiento

    private double _angle = 0;                  // Angulo de rotacion
    private double _speed = 150;                // Velocidad de rotacion

    private Utils.Point _pOrig;                 // Punto origen del segmento transitado
    private Utils.Point _pDest;                 // Punto destino del segmento transitado
    private Utils.Point _vCurSeg;               // Vector segmento
    private Utils.Point _vDir;                  // Vector direccion

    private Level _currentLevel = null;
    private Path _currentPath = null;           // Trazo en el que está el player
    private int _currentLineIndex;              // Id de la linea en la que esta el player
    //private int _realCurrentLineIndex;          // Id de la linea en la que esta el player
    private int _currentPathIndex = 0;          // Id del trazo en el que esta el player

    private boolean _jumping = false;           // Determina si se encuentra saltando
    private double _jumpVelocity = 1000;        // Velocidad de salto
    private Utils.Point _originJumpPosition;    // Posicion desde donde se ha saltado


    Player(){
        _pLastPosition = new Utils.Point(0, 0);
        _pPosition = new Utils.Point(0, 0);
        _originJumpPosition = new Utils.Point(0, 0);

        _pOrig = new Utils.Point(0,0);
        _pDest = new Utils.Point(0,0);
        _vCurSeg = new Utils.Point(0,0);
        _vDir = new Utils.Point(0,0);

        _velocity = _translateVelocity;
        _currentLineIndex = -_orientation;
        //_realCurrentLineIndex = -_orientation;
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

    void setCurrentLevel(Level level){
        _currentLevel = level;
        _currentPath = _currentLevel.getPaths().get(0);
        updateCurSeg();
    }

    /**
     * Actualiza el segmento en el que se mueve el player
     * */
    private void updateCurSeg(){

        int dest = 0, orig = 0;
        // Calculamos ids de los puntos origen y destino del siguiente segmento en funcion
        // de la orientacion que hay que seguir
        if(_orientation == 1) {
            _currentLineIndex = (_currentLineIndex + 1) % _currentPath.getVertexes().size();
            orig = _currentLineIndex;
            dest = (_currentLineIndex + 1) % _currentPath.getVertexes().size();
            //_realCurrentLineIndex = orig;
        }
        else if(_orientation == -1){
            orig = _currentLineIndex;
            _currentLineIndex = _currentLineIndex - 1 < 0 ? _currentPath.getVertexes().size()-1 : _currentLineIndex - 1;
            dest = _currentLineIndex;
            //_realCurrentLineIndex = dest;
        }

        // Asignamos los valores calculados
        int origId = orig;
        int destId = dest;

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

        System.out.println("Segmento: " + _currentLineIndex);
        //System.out.println("Segmento Real: " + _realCurrentLineIndex);
    }

    /**
     * Cambia la direccion de traslacion y la velocidad a las de salto
     * */
    private void jump(){
        _jumping = true;

        // actualizo los valores necerarios para que se mueva correctamente
        _originJumpPosition.x = _pPosition.x;
        _originJumpPosition.y = _pPosition.y;

        _vDir.x = _currentPath.getDirections().get(_currentLineIndex).x;
        _vDir.y = _currentPath.getDirections().get(_currentLineIndex).y;

        // cambio la direccion de traslacion
        _orientation = -_orientation;

        // velocidad de salto
        _velocity = _jumpVelocity;
    }

    private Utils.Point checkCollision() {

        Utils.Point p = null;
        // recorremos todas las lineas de los trazos del nivel
        for (int j = 0; j < _currentLevel.getPaths().size(); j++) {
            for (int i = 0; i < _currentLevel.getPaths().get(j).getVertexes().size() && p == null; i++) {
                // nos saltamos el trazo y linea en la que está el player
                if (_currentPathIndex == j && _currentLineIndex == i) {
                    continue;
                }
                List<Utils.Point> points = _currentLevel.getPaths().get(j).getVertexes();
                double xori = points.get(i).x;
                double yori = points.get(i).y;
                double xdest = points.get((i + 1) % _currentPath.getVertexes().size()).x;
                double ydest = points.get((i + 1) % _currentPath.getVertexes().size()).y;

                p = Utils.segmentsIntersection(_pLastPosition.x, _pLastPosition.y,
                        _pPosition.x, _pPosition.y, xori, yori, xdest, ydest);

                if (p != null && _jumping) {
                    _currentPathIndex = j;
                    _currentPath = _currentLevel.getPaths().get(j);
                    _currentLineIndex = i - _orientation;
                    updateCurSeg();

                    _pPosition = p;
                    _jumping = false;
                    _velocity = _translateVelocity;

                }
            }
        }
        return p;
    }

    void handleInput(List<Input.TouchEvent> events) {
        if(events == null)
            return;

        for (Input.TouchEvent e:events) {
            if(!_jumping &&e._type == Input.Type.press){
                jump();
            }
        }
    }

    void update(double deltaTime){
        // Actualizamos la rotacion
        _angle = (_angle + _speed * deltaTime) % 360;

        updatePosition(deltaTime);

        checkCollision();

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

    private void updatePosition(double deltaTime){
        // Trasladamos en la direccion calculada en updateCurSeg()

        _pLastPosition.x = _pPosition.x;
        _pLastPosition.y = _pPosition.y;
        _pPosition.x += _vDir.x*_velocity*deltaTime;
        _pPosition.y += _vDir.y*_velocity*deltaTime;

        if(!_jumping){
            // Hallamos distancia recorrida orig-position
            Utils.Point vDistRecor = new Utils.Point(_pPosition.x - _pOrig.x, _pPosition.y - _pOrig.y);

            // Comprobamos que la distancia recorrida no sea mayor que el segmento
            // restando las magnitudes de los vectores _vCurSeg y vDistRecor
            if(_vCurSeg.magnitud() - vDistRecor.magnitud() < 0)
                // si es negativa cambiamos de segmanto
                updateCurSeg();
        }
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
