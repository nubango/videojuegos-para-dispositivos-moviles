package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.Graphics;

public class Level {
    int _numLevel;
    String _name;
    ArrayList<Path> _paths;
    ArrayList<Item> _items;
    ArrayList<Enemy> _enemies;

    Level(int numLevel, String name, ArrayList<Path> paths, ArrayList<Item> items){
        _numLevel = numLevel;
        _name = name;
        _paths = new ArrayList<>(paths);
        _items = new ArrayList<>(items);

    }

    void addEnemies(ArrayList<Enemy> enemies){
        _enemies = new ArrayList<>(enemies);
    }

    void update(double deltaTime){
        for (Path p: _paths) {
            p.update(deltaTime);
        }

        for (Item i: _items) {
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
        // asignar la fuente
        // pintar el nombre
        g.drawText(_name, 10, 10);

        for (Path p: _paths) {
            p.render(g);
        }

        for (Item i: _items) {
            i.render(g);
        }

        if(_enemies != null) {
            for (Enemy e : _enemies) {
                e.render(g);
            }
        }
    }
}
