package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player {
    private Utils.Point _position;
    private Utils.Point _lastPosition;
    private Utils.Point _originJumpPosition;
    private int _tam = 6;           // Tamaño de los segmentos que forman el item
    private int _scale = 1;
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

    private Level currentLevel;

    Player(double x, double y){
        set_lastPosition(new Utils.Point(x, y));
        set_position(new Utils.Point(x, y));
        set_originJumpPosition(new Utils.Point(x, y));

         set_direction(new Utils.Point(1,1));
         set_vDest(new Utils.Point(x,y));

         set_velocity(get_translateVelocity());
    }

    void updatePathCoordinates(){

        int dest, orig;
        if(get_dir() < 0) {
            dest = get_currentLine();
            orig = (get_currentLine() + 1) % get_currentPath().get_vertexes().size();
        } else {
            orig = get_currentLine();
            dest = (get_currentLine() + 1) % get_currentPath().get_vertexes().size();
        }

        // Asignamos los valores
        double xOrigin = get_currentPath().get_vertexes().get(orig).getX();
        double yOrigin = get_currentPath().get_vertexes().get(orig).getY();

        get_vDest().x = get_currentPath().get_vertexes().get(dest).getX();
        get_vDest().y = get_currentPath().get_vertexes().get(dest).getY();

        get_position().x = xOrigin;
        get_position().y = yOrigin;

        double xDir = get_vDest().x - xOrigin;
        double yDir = get_vDest().y - yOrigin;

        // Normalizamos coordenadas del vector
        double length = Math.sqrt( xDir*xDir + yDir*yDir );
        if (length != 0) {
            xDir = xDir/length;
            yDir = yDir/length;
        }

        get_direction().x = xDir;
        get_direction().y = yDir;

    }

    void updateJumpCoordinates(){
        // actualizo los valores necerarios para que se mueva correctamente
        get_originJumpPosition().x = get_position().x;
        get_originJumpPosition().y = get_position().y;

        get_direction().x = get_currentPath().get_directions().get(get_currentLine()).getX();
        get_direction().y = get_currentPath().get_directions().get(get_currentLine()).getY();

        // cambio la direccion de traslacion
        set_dir(-get_dir());

        // velocidad de salto
        set_velocity(get_jumpVelocity());

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
        for (int i = 0; i < get_currentPath().get_vertexes().size() && p == null; i++) {
            if (i != get_currentLine()) {
                double xori = get_currentPath().get_vertexes().get(i).x;
                double yori = get_currentPath().get_vertexes().get(i).y;
                double xdest = get_currentPath().get_vertexes().get((i + 1) % get_currentPath().get_vertexes().size()).x;
                double ydest = get_currentPath().get_vertexes().get((i + 1) % get_currentPath().get_vertexes().size()).y;

                p = Utils.segmentsIntersection(get_lastPosition().x, get_lastPosition().y, get_position().x, get_position().y, xori, yori, xdest, ydest);

                if (p != null) {
                    set_currentLine(i);
                }
            }
        }

        return p;
    }

    void setCurrentPath(Path path){
        set_currentPath(path);

        updatePathCoordinates();

    }

    void handleInput(List<Input.TouchEvent> events) {
        if(events == null)
            return;

        for (Input.TouchEvent e:events) {
            if(!is_jumping() &&e._type == Input.Type.press){
                set_jump(true);
            }
        }
    }

    void update(double deltaTime){
        set_angle((get_angle() + get_speed() * deltaTime) % 360);

        // Traslacion del player
        get_lastPosition().x = get_position().getX();
        get_lastPosition().y = get_position().getY();
        get_position().x += get_direction().x * get_velocity() * deltaTime;
        get_position().y += get_direction().y * get_velocity() * deltaTime;


        if(is_jump()){
            set_jump(false);
            set_jumping(true);
            updateJumpCoordinates();
        }
        else if(is_jumping()) {

            Utils.Point p = checkCollision();
            // comprobar colision con linea
            //if (xPosition > xDestination || yPosition > yDestination) {
            if (p != null) {
                set_jumping(false);
                set_velocity(get_translateVelocity());

                updatePathCoordinates();

                get_position().x = (int)p.x;
                get_position().y = (int)p.y;
            }
        }
        else if(!is_jumping()) {

            // Multiplicamos los valores por la direccion para poder compararlos
            double xPosition = get_position().x * get_direction().x;
            double yPosition = get_position().y * get_direction().y;
            double xDestination = get_vDest().x * get_direction().x;
            double yDestination = get_vDest().y * get_direction().y;

            // Si el player se sale del camino marcado por _vOrigin-_vDest
            if (xPosition > xDestination || yPosition > yDestination) {

                set_currentLine(get_currentLine() + get_dir());
                if(get_currentLine() < 0)
                    set_currentLine(get_currentPath().get_vertexes().size() - 1);
                else if(get_currentLine() >= get_currentPath().get_vertexes().size())
                    set_currentLine(get_currentLine() % get_currentPath().get_vertexes().size());

                updatePathCoordinates();
            }
        }
    }

    void render(Graphics g){
        g.setColor(get_color());

        if(!g.save()) {
            return;
        }

        // pintamos la linea al saltar
        if(is_jumping())
            g.drawLine((int) get_position().x, (int) get_position().y, (int) get_originJumpPosition().x, (int) get_originJumpPosition().y);

        g.translate((int) get_position().x, (int) get_position().y);
        g.scale(get_scale(), get_scale());
        g.rotate(get_angle());



        g.drawLine(-get_tam(),-get_tam(), get_tam(),-get_tam());
        g.drawLine(get_tam(),-get_tam(), get_tam(), get_tam());
        g.drawLine(get_tam(), get_tam(), -get_tam(), get_tam());
        g.drawLine(-get_tam(), get_tam(), -get_tam(),-get_tam());

        g.restore();
    }

    void setCurrentLevel (Level level){
        currentLevel = level;
    }

    public Utils.Point get_position() {
        return _position;
    }

    public void set_position(Utils.Point _position) {
        this._position = _position;
    }

    public Utils.Point get_lastPosition() {
        return _lastPosition;
    }

    public void set_lastPosition(Utils.Point _lastPosition) {
        this._lastPosition = _lastPosition;
    }

    public Utils.Point get_originJumpPosition() {
        return _originJumpPosition;
    }

    public void set_originJumpPosition(Utils.Point _originJumpPosition) {
        this._originJumpPosition = _originJumpPosition;
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

    public int get_lifes() {
        return _lifes;
    }

    public void set_lifes(int _lifes) {
        this._lifes = _lifes;
    }

    public int get_color() {
        return _color;
    }

    public void set_color(int _color) {
        this._color = _color;
    }

    public double get_angle() {
        return _angle;
    }

    public void set_angle(double _angle) {
        this._angle = _angle;
    }

    public double get_speed() {
        return _speed;
    }

    public void set_speed(double _speed) {
        this._speed = _speed;
    }

    public double get_velocity() {
        return _velocity;
    }

    public void set_velocity(double _velocity) {
        this._velocity = _velocity;
    }

    public double get_translateVelocity() {
        return _translateVelocity;
    }

    public void set_translateVelocity(double _translateVelocity) {
        this._translateVelocity = _translateVelocity;
    }

    public double get_jumpVelocity() {
        return _jumpVelocity;
    }

    public void set_jumpVelocity(double _jumpVelocity) {
        this._jumpVelocity = _jumpVelocity;
    }

    public Utils.Point get_direction() {
        return _direction;
    }

    public void set_direction(Utils.Point _direction) {
        this._direction = _direction;
    }

    public Utils.Point get_vDest() {
        return _vDest;
    }

    public void set_vDest(Utils.Point _vDest) {
        this._vDest = _vDest;
    }

    public int get_dir() {
        return _dir;
    }

    public void set_dir(int _dir) {
        this._dir = _dir;
    }

    public Path get_currentPath() {
        return _currentPath;
    }

    public void set_currentPath(Path _currentPath) {
        this._currentPath = _currentPath;
    }

    public int get_currentPathIndex() {
        return _currentPathIndex;
    }

    public void set_currentPathIndex(int _currentPathIndex) {
        this._currentPathIndex = _currentPathIndex;
    }

    public int get_currentLine() {
        return _currentLine;
    }

    public void set_currentLine(int _currentLine) {
        this._currentLine = _currentLine;
    }

    public boolean is_jump() {
        return _jump;
    }

    public void set_jump(boolean _jump) {
        this._jump = _jump;
    }

    public boolean is_jumping() {
        return _jumping;
    }

    public void set_jumping(boolean _jumping) {
        this._jumping = _jumping;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
