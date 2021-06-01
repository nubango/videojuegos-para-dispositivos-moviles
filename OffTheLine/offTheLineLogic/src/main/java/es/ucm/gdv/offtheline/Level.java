package es.ucm.gdv.offtheline;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;

public class Level {
    private int _numLevel;
    private String _name;
    private ArrayList<Path> _paths;
    private ArrayList<Item> _items;
    private ArrayList<Enemy> _enemies = null;
    private Font _font = null;

    Level(int numLevel, String name, ArrayList<Path> paths, ArrayList<Item> items){
        _numLevel = numLevel;
        _name = name;
        _paths = new ArrayList<>(paths);
        _items = new ArrayList<>(items);
    }

    void setFont(Font f){ _font = f; }

    void addEnemies(ArrayList<Enemy> enemies){
        _enemies = new ArrayList<>(enemies);
    }

    void update(double deltaTime){
        for (Path p: _paths) {
            p.update(deltaTime);
        }

        for (Item i: _items) {
            if(!i._taken)
                i.update(deltaTime);
        }

        if(_enemies != null)
        {
            for (Enemy e: _enemies) {
                e.update(deltaTime);
            }
        }
    }

    void render(Graphics g){

        drawText(g);

        for (Path p: _paths) {
            p.render(g);
        }

        for (Item i: _items) {
            if(!i._taken)
                i.render(g);
        }

        if(_enemies != null) {
            for (Enemy e : _enemies) {
                e.render(g);
            }
        }
    }

    private void drawText(Graphics g){
        g.setColor(0xFFFFFFFF);
        int level = _numLevel + 1;
        g.setFont(_font);
        g.drawText("Level " + level + " - " + _name,60, 10);
    }
    ArrayList<Path> getPaths(){ return _paths; }
    ArrayList<Item> getItems(){ return _items; }
    ArrayList<Enemy> getEnemies(){ return _enemies; }
}
