package es.ucm.gdv.offtheline;

import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Graphics;

public class Path {

    List<Utils.Point> _vertexes;    // Lista de puntos que son los vercices de las lineas
    List<Utils.Point> _directions;
    private int _scale = 1;
    private int _color = 0xFFFFFFFF;

    // El contructor hay que hacerlo de otra forma porque ahora solo pintaría bien los niveles que tienen un único camino
    Path(List<Utils.Point> vertexes, List<Utils.Point> directions){
    //Path(List<Integer> vertexes){
        _vertexes = new ArrayList<>(vertexes);
        if(directions != null)
            _directions = new ArrayList<>(directions);
        else{
            _directions = new ArrayList<>();
            for (int i = 0; i < _vertexes.size(); i++) {

                double xOrigin = _vertexes.get(i).getX();
                double yOrigin = _vertexes.get(i).getY();
                double xDest = _vertexes.get((i+1)%_vertexes.size()).getX();
                double yDest = _vertexes.get((i+1)%_vertexes.size()).getY();;

                // Hallamos coordenadas del vector direccion
                double xDir = xDest - xOrigin;
                double yDir = yDest - yOrigin;

                // Normalizamos coordenadas del vector
                double length = Math.sqrt( xDir*xDir + yDir*yDir );
                if (length != 0) {
                    xDir = xDir/length;
                    yDir = yDir/length;
                }

                _directions.add(new Utils.Point(xDir, yDir));
            }
        }
    }

    void update(double deltaTime){

    }

    void render(Graphics g){

        g.setColor(_color);

        if(g.save()) {
            g.translate(0, 0);
            g.scale(_scale, _scale);
        }

        // Comprobamos que el tamaño de la lista es múltiplo de 2 y recorremos la lista de vértices de 2 en 2.
        // Cuando llegamos al ultimo vértice lo que hacemos es unirlo con el primero por eso hacemos % size()
        for (int i = 0; i < _vertexes.size(); i++){
                // dividimos entre la escala porque queremos escalar solo el grosor de la linea, no las posiciones
                g.drawLine((int)_vertexes.get(i).x/_scale,(int)_vertexes.get(i).y/_scale,
                        (int)_vertexes.get((i+1)%_vertexes.size()).x/_scale,
                        (int)_vertexes.get((i+1)%_vertexes.size()).y/_scale);
        }

        g.restore();

    } // render

}
