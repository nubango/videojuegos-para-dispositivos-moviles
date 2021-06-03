package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player {

    private int _lifes = 10;        // Vidas del jugador

    // Nuevo-----------------------------------------------
    private Utils.Point _pPosition;             // Posicion actual
    private Utils.Point _pLastPosition;         // Posicion anterior

    private int _color = 0xFF0081F9;            // Color del player
    private int _scale = 1;                     // Escala del player
    private int _tam = 6;                       // Tamaño de los segmentos que forman el item
    private double umbralItemCollision = 20;    // Distancia a la que coges un item

    private double _velocity = 0;               // Velocidad actual
    private double _translateVelocity = 200;    // Velocidad de traslacion del player
    private int _orientation = 1;               // Sentido de movimiento

    private double _angle = 0;                  // Angulo de rotacion
    private double _speed = 150;                // Velocidad de rotacion

    private Utils.Point _pV1;                   // Punto 1 del segmento transitado
    private Utils.Point _pV2;                   // Punto 2 del segmento transitado
    private Utils.Vector _vCurSeg;               // Vector segmento actual
    private Utils.Vector _vDir;                  // Vector direccion actual
    private Utils.Vector _vLastDir;              // Vector direccion

    private Level _currentLevel = null;
    private Path _currentPath = null;           // Trazo en el que está el player
    private int _currentLineIndex;              // Id de la linea en la que esta el player
    private int _currentPathIndex = 0;          // Id del trazo en el que esta el player

    private boolean _jumping = false;           // Determina si se encuentra saltando
    private double _jumpVelocity = 1500;        // Velocidad de salto
    private Utils.Point _originJumpPosition;    // Posicion desde donde se ha saltado

    private boolean _deathAnimation = false;    // Saber si el player ha muerto o no
    private boolean _deathOutBounds = false;    // Saber si el player ha muerto o no
    private int _numLinesAnimation = 10;        // cantidad de lineas que salen al morir
    private Utils.Vector[] _linesAnimationDir;  // una direccion por cada linea
    private Utils.Vector[] _linesAnimationPos;  // una posicion por cada linea
    private double[] _linesAnimationAngle;      // angulo de rotacion por cada linea
    private double _lineVelocityDeathAnimation = 15;    // velocidad de traslacion de las lineas
    private double _lineSpeedDeathAnimation = 50;       // velocidad de rotacion de las lineas
    private double _elapsedTime = 0;                    // tiempo transcurrido para controlar la animacion
    private double _deathAnimationTime = 3;             // tiempo que dura la animacion de muerte


    Player(){
        _pLastPosition = new Utils.Point(0, 0);
        _pPosition = new Utils.Point(0, 0);
        _originJumpPosition = new Utils.Point(0, 0);

        _pV1 = new Utils.Point(0,0);
        _pV2 = new Utils.Point(0,0);
        _vCurSeg = new Utils.Vector(0,0);
        _vDir = new Utils.Vector(0,0);
        _vLastDir = new Utils.Vector(0,0);

        _velocity = _translateVelocity;
        _currentLineIndex = -1;
        _linesAnimationDir = new Utils.Vector[_numLinesAnimation];
        _linesAnimationPos = new Utils.Vector[_numLinesAnimation];
        _linesAnimationAngle = new double[_numLinesAnimation];
    }

    boolean isDeath(){ return _deathAnimation || _deathOutBounds; }

    void setCurrentLevel(Level level){
        _currentLevel = level;
        _currentPath = _currentLevel.getPaths().get(0);
        // reseteamos los valores

        _currentPathIndex = 0;
        _currentLineIndex = -1;
        _orientation = 1;
        _velocity = _translateVelocity;
        _jumping = false;

        nextSegment();
    }

    /**
     * Cambia la direccion de traslacion y la velocidad a las de salto
     * */
    private void jump(){
        _jumping = true;

        // actualizo los valores necerarios para que se mueva correctamente
        _originJumpPosition.x = _pPosition.x;
        _originJumpPosition.y = _pPosition.y;

        _vLastDir.x = _vDir.x;
        _vLastDir.y = _vDir.y;
        _vDir.x = _currentPath.getJumpDirections().get(_currentLineIndex).x;
        _vDir.y = _currentPath.getJumpDirections().get(_currentLineIndex).y;

        // velocidad de salto
        _velocity = _jumpVelocity;
    }

    private void checkItemsCollision(){
        for (int j = 0; j < _currentLevel.getItems().size(); j++) {
            double distItem = Utils.sqrDistancePointSegment(_pLastPosition, _pPosition,
                    _currentLevel.getItems().get(j).getPosition());
            if(distItem <= umbralItemCollision * umbralItemCollision) {
                _currentLevel.getItems().get(j).takeItem(_currentLevel);

            }
        }
    }

    private void startDeathAction(){
        _deathAnimation = true;
        _elapsedTime = 0;
        for(int i = 0; i < _numLinesAnimation; i++){
            _linesAnimationAngle[i] = Math.random()*360;
            _linesAnimationDir[i] = new Utils.Vector(Math.random(), Math.random());
            _linesAnimationPos[i] = new Utils.Vector(0, 0);
        }
    }

    private boolean deathAnimation(double deltaTime){
        _elapsedTime += deltaTime;
        if(_deathAnimation)
            for (int i = 0; i < _numLinesAnimation; i++) {
                _linesAnimationAngle[i] += _lineSpeedDeathAnimation * deltaTime;
                _linesAnimationPos[i].x += _linesAnimationDir[i].x * _lineVelocityDeathAnimation * deltaTime;
                _linesAnimationPos[i].y += _linesAnimationDir[i].y * _lineVelocityDeathAnimation * deltaTime;
            }

        if(_elapsedTime > _deathAnimationTime)
            return true;
        return false;
    }

    private void endDeathAction(){
        _deathAnimation = false;
        _deathOutBounds = false;
        setCurrentLevel(_currentLevel);
        _currentLevel.resetLevel();
        // lamamos a el metodo correspondiente de level
    }

    private void checkEnemiesCollision() {
        if (_pPosition.x > OffTheLineLogic.LOGIC_WIDTH / 1.9 || _pPosition.x < -OffTheLineLogic.LOGIC_WIDTH / 1.9 ||
                _pPosition.y > OffTheLineLogic.LOGIC_HEIGHT / 1.9 || _pPosition.y < -OffTheLineLogic.LOGIC_HEIGHT / 1.9) {
            _deathOutBounds = true;
            _elapsedTime = 0;
        }
        else if(_currentLevel.getEnemies()!=null)
            for (int j = 0; j < _currentLevel.getEnemies().size(); j++) {
                Utils.Point[] points = _currentLevel.getEnemies().get(j).getExtremPoints();
                if(points[0] != null && points[1] != null){
                    Utils.Point p = Utils.segmentsIntersection(_pLastPosition, _pPosition, points[0], points[1]);
                    if(p != null) {
                        // Iniciamos animacion del personaje
                        startDeathAction();
                        // Cuando acabe la animacion reseteamos el nivel completo

                    }
                }
            }
    }

    /**
     * Compruba si colisiona con algun segmento
     * recorre todos los segmentos de todos los caminos del nivel
     * los segmentos vienen dados por dos puntos, el inicial y el final
     *
     * Se comprueba si hay interseccion entre el segmento y el vector formado por la posicion
     * anterior y la actual del player
     *
     * Si devuelve un punto es que ha habido interseccion y se actualizan los valores necesarios
     * para cambiar la direccion y el segmento que tiene que seguir a partir de ahora
     *
     * */
    private Utils.Point checkPathsCollision() {

        Utils.Point p = null;
        // recorremos todas las lineas de los trazos del nivel
        for (int j = 0; j < _currentLevel.getPaths().size() && p == null; j++) {
            for (int i = 0; i < _currentLevel.getPaths().get(j).getVertexes().size() && p == null; i++) {
                Utils.Point iPointV1 = _currentLevel.getPaths().get(j).getVertexes().get(i);
                Utils.Point iPointV2 = _currentLevel.getPaths().get(j).getVertexes().
                        get((i+1) % _currentLevel.getPaths().get(j).getVertexes().size());

                // nos saltamos la linea en la que está el player
                boolean sameLine = iPointV1.samePoint(_pV1) && iPointV2.samePoint(_pV2)||
                        iPointV1.samePoint(_pV2) && iPointV2.samePoint(_pV1);;
                if (_currentPathIndex == j && sameLine) {
                    continue;
                }

                List<Utils.Point> points = _currentLevel.getPaths().get(j).getVertexes();
                double xV1 = points.get(i).x;
                double yV1 = points.get(i).y;
                double xV2 = points.get((i + 1) % _currentLevel.getPaths().get(j).getVertexes().size()).x;
                double yV2 = points.get((i + 1) % _currentLevel.getPaths().get(j).getVertexes().size()).y;

                p = Utils.segmentsIntersection(_pLastPosition, _pPosition, new Utils.Point(xV1, yV1),
                                                                           new Utils.Point(xV2, yV2));

                if (p != null) {
                    _currentPathIndex = j;
                    _currentPath = _currentLevel.getPaths().get(j);
                    _currentLineIndex = i;
                    _pV1 = new Utils.Point(xV1, yV1);
                    _pV2 = new Utils.Point(xV2, yV2);

                    calculateNewDir();

                    _pPosition = p;
                    _jumping = false;
                    _velocity = _translateVelocity;

                }
            }
        }
        return p;
    }

    /**
     * Calcular direccion correcta cuando llegue al nuevo segmento despues de saltar
     * Si el angulo que forman es < 90 = newDir | > 90 = -newDir -> esto es para que
     * siga la direccion que mejor siente al desplazamiento que tenia antes de saltar
     *
     * coseno del angulo que forman es positivo = angulo < 90 | negativo = angulo > 90
     * */
    private void calculateNewDir() {
        // Calculamos la nueva direccion
        Utils.Vector vNewDir = new Utils.Vector(_pV2.x - _pV1.x ,_pV2.y - _pV1.y);
        vNewDir.normalize();
        double scalarProduct = vNewDir.scalarProduct(_vLastDir);
        double cos = scalarProduct / (vNewDir.module() * _vLastDir.module());

        // tenemos que actualizar la orientacion y el segmento actual en funcion de la direccion que escojamos
        _vDir.x = vNewDir.x;
        _vDir.y = vNewDir.y;
        _vCurSeg.x = _pV2.x - _pV1.x;
        _vCurSeg.y = _pV2.y - _pV1.y;
        _orientation = 1;

        if(cos < 0) {
            _vDir.changeOppositeDirection();
            _vCurSeg.x = _pV1.x - _pV2.x;
            _vCurSeg.y = _pV1.y - _pV2.y;
            _orientation = -1;
        }

    }

    void handleInput(List<Input.TouchEvent> events) {
        if(events == null)
            return;

        for (Input.TouchEvent e:events) {
            if(!_jumping &&e._type == Input.Type.press){
                jump();
                //startDeathAction();
            }
        }
    }

    void update(double deltaTime) {

        if (!_deathAnimation && !_deathOutBounds) {
            // Actualizamos la rotacion
            _angle = (_angle + _speed * deltaTime) % 360;
            updatePosition(deltaTime);

            if (_jumping) {
                checkPathsCollision();
                checkItemsCollision();
            }

            checkEnemiesCollision();
        }
        else if(deathAnimation(deltaTime)){
            endDeathAction();
        }
    }

    private void updatePosition(double deltaTime){
        // Trasladamos en la direccion calculada en updateCurSeg()

        _pLastPosition.x = _pPosition.x;
        _pLastPosition.y = _pPosition.y;
        _pPosition.x += _vDir.x*_velocity*deltaTime;
        _pPosition.y += _vDir.y*_velocity*deltaTime;

        if(!_jumping){
            // Hallamos distancia recorrida orig-position
            Utils.Vector vDistRecor;
            if(_orientation == 1)
                vDistRecor = new Utils.Vector(_pPosition.x - _pV1.x, _pPosition.y - _pV1.y);
            else
                vDistRecor = new Utils.Vector(_pPosition.x - _pV2.x, _pPosition.y - _pV2.y);

            // Comprobamos que la distancia recorrida no sea mayor que el segmento
            // restando las magnitudes de los vectores _vCurSeg y vDistRecor
            if(_vCurSeg.module() - vDistRecor.module() < 0)
                // si es negativa cambiamos de segmanto
                nextSegment();
        }
    }

    /**
     * Actualiza el segmento en el que se mueve el player
     * */
    private void nextSegment(){

        int v2Index = 0, v1Index = 0;

        // Calculamos ids de los puntos origen y destino del siguiente segmento
        if(_orientation == 1) {
            _currentLineIndex = (_currentLineIndex + 1) % _currentPath.getVertexes().size();
        }
        else {
            int aux = _currentLineIndex - 1;
            _currentLineIndex = aux < 0 ? _currentPath.getVertexes().size() - 1 : aux;
        }

        // Asigno valores a los vertices del segmento
        v1Index = _currentLineIndex;
        v2Index = (_currentLineIndex + 1) % _currentPath.getVertexes().size();
        // Hago el % otra vez porque hay un supuesto en el que si pulsas muy rápido
        // el boton de salto, el valor de v1Index es 4 y se produce una excepcion
        v1Index %= _currentPath.getVertexes().size();
        v2Index %= _currentPath.getVertexes().size();


        // Creamos los puntos origen y destino del primer segmento del trazo
        double xV1 = _currentPath.getVertexes().get(v1Index).x;
        double yV1 = _currentPath.getVertexes().get(v1Index).y;
        _pV1.x = xV1;
        _pV1.y = yV1;

        double xV2 = _currentPath.getVertexes().get(v2Index).x;
        double yV2 = _currentPath.getVertexes().get(v2Index).y;
        _pV2.x = xV2;
        _pV2.y = yV2;

        updateDir();

        // System.out.println("Segmento: " + _currentLineIndex);
    }

    /**
     * Actualiza la direccion en funcion de la orientacion y del segmento en el que se encuentre
     *
     * */
    private void updateDir(){
        // Creamos el vector del segmento

        double x = _orientation == 1 ? _pV2.x - _pV1.x : _pV1.x - _pV2.x;
        double y = _orientation == 1 ? _pV2.y - _pV1.y : _pV1.y - _pV2.y;

        _vCurSeg.x = x;
        _vCurSeg.y = y;

        // Creamos y normalizamos el vector direccion
        _vDir.x = x;
        _vDir.y = y;
        _vDir.normalize();

        _pPosition.x = _orientation == 1 ? _pV1.x : _pV2.x;
        _pPosition.y = _orientation == 1 ? _pV1.y : _pV2.y;
    }

    void render(Graphics g){
        g.setColor(_color);

        int side = _tam / _scale;


        if(!_deathAnimation) {
            if(!g.save()) {
                return;
            }

            g.translate(_pPosition.x + OffTheLineLogic.LOGIC_WIDTH / 2, -_pPosition.y +
                    OffTheLineLogic.LOGIC_HEIGHT / 2);

            g.scale(_scale, _scale);
            g.rotate(_angle);


            g.drawLine(-side, -side, side, -side);
            g.drawLine(side, -side, side, side);
            g.drawLine(side, side, -side, side);
            g.drawLine(-side, side, -side, -side);

            g.restore();
        }
        else{

            for (int i = 0; i < _numLinesAnimation; i++) {
                if(!g.save()) {
                    return;
                }

                g.translate(_linesAnimationPos[i].x+_pPosition.x + OffTheLineLogic.LOGIC_WIDTH / 2, _linesAnimationPos[i].y-_pPosition.y +
                        OffTheLineLogic.LOGIC_HEIGHT / 2);

                g.scale(_scale, _scale);
                g.rotate(_linesAnimationAngle[i]);

                g.drawLine(-side, -side, side, -side);

                g.restore();
            }
        }

        //jumpLineDebug(g);
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



