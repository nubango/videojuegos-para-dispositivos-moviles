package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player {
    Utils.Point _position;
    int _tam = 6;           // Tamaño de los segmentos que forman el item
    int _scale = 1;
    int _lifes = 10;        // Vidas del jugador
    int _color = 0xFF0081F9;

    double _angle = 0;      // Angulo de rotacion
    double _speed = 150;    // Velocidad de rotacion

    double _velocity = 100;
    Utils.Point _direction;  // Velocidad de traslacion
    Utils.Point _vOrigin;  // Vertice origen, de donde vengo
    Utils.Point _vDest;    // Vertice destino, a donde voy

    Path _currentPath;
    int _currentLine = 0;


    Player(double x, double y){
        _position = new Utils.Point(x, y);

         _direction = new Utils.Point(1,1);
         _vOrigin = new Utils.Point(x,y);
         _vDest = new Utils.Point(x,y);
    }

    void updatePathCoordinates(){
        double xOrigin = _currentPath._vertexes.get(_currentLine).getX();
        double yOrigin = _currentPath._vertexes.get(_currentLine).getY();

        double xDest = _currentPath._vertexes.get((_currentLine+1)%_currentPath._vertexes.size()).getX();
        double yDest = _currentPath._vertexes.get((_currentLine+1)%_currentPath._vertexes.size()).getY();

        // Hallamos coordenadas del vector direccion
        double xDir = xDest - xOrigin;
        double yDir = yDest - yOrigin;

        // Normalizamos coordenadas del vector
        double length = Math.sqrt( xDir*xDir + yDir*yDir );
        if (length != 0) {
            xDir = xDir/length;
            yDir = yDir/length;
        }

        // Asignamos los valores
        _position.x = xOrigin;
        _position.y = yOrigin;

        _vOrigin.x = xOrigin;
        _vOrigin.y = yOrigin;

        _vDest.x = xDest;
        _vDest.y = yDest;

        _direction.x = xDir;
        _direction.y = yDir;
    }

    void setCurrentPath(Path path){
        _currentPath = path;

        updatePathCoordinates();

    }

    void update(double deltaTime){
        _angle = (_angle + _speed * deltaTime) % 360;

        // Traslacion del enemigo
        _position.x += _direction.x * _velocity * deltaTime;
        _position.y += _direction.y * _velocity * deltaTime;


        // Multiplicamos los valores por la direccion para poder compararlos
        double xPosition = _position.x*_direction.x;
        double yPosition = _position.y*_direction.y;
        double xDestination = _vDest.x*_direction.x;
        double yDestination = _vDest.y*_direction.y;

        // Si el player se sale del camino marcado por _vOrigin-_vDest

        // Vamos a pintar fuera del intervalo. Rectificamos e iniciamos la cuenta de parado
        if (xPosition > xDestination || yPosition > yDestination) {
            _currentLine = (_currentLine+1)%_currentPath._vertexes.size();
            updatePathCoordinates();
        }

    }

    void render(Graphics g){
        g.setColor(_color);

        if(!g.save()) {
            return;
        }

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
