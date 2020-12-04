package es.ucm.gdv.offtheline;

import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Graphics;
import sun.rmi.server.InactiveGroupException;

public class Path {

    List<Integer> _vertexes;    // Lista de coordenadas de los vércices i0=x1,i1=y1,i2=x2,i3=y2,...
    List<Integer> _directions;
    int _scale = 1;
    int _color = 0xFFFFFFFF;

    // El contructor hay que hacerlo de otra forma porque ahora solo pintaría bien los niveles que tienen un único camino
    //Path(List<Integer> vertexes, List<Integer> directions){
    Path(List<Integer> vertexes){
        _vertexes = new ArrayList<>(vertexes);
        //_directions = new ArrayList<>(directions);
    }

    void update(double deltaTime){

    }

    void render(Graphics g){

        g.setColor(_color);

        if(g.save()) {
            g.translate(g.getWidth()/2, g.getHeight()/2);
            g.scale(_scale, _scale);
        }

        // Comprobamos que el tamaño de la lista es múltiplo de 2 y recorremos la lista de vértices de 2 en 2.
        // Cuando llegamos al ultimo vértice lo que hacemos es unirlo con el primero por eso hacemos % size()
        for (int i = 0; i < _vertexes.size() && _vertexes.size()%2 == 0; i+=2){
            // dividimos entre la escala porque queremos escalar solo el grosor de la linea, no las posiciones
            g.drawLine(_vertexes.get(i)/_scale,_vertexes.get(i+1)/_scale,_vertexes.get((i+2)%_vertexes.size())/_scale,_vertexes.get((i+3)%_vertexes.size())/_scale);
        }

        g.restore();

    } // render

}
