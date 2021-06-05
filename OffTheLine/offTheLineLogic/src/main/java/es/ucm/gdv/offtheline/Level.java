package es.ucm.gdv.offtheline;

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
    private int _numItems = 0;
    private Game _logic = null;

    private double _timeWaitToNextLevel = 1;    // Tiempo de espera hasta que pasa al siguente nivel (en segundos)
    private double _elapsedTime = 0;

    Level(int numLevel, String name, ArrayList<Path> paths, ArrayList<Item> items){
        _numLevel = numLevel;
        _name = name;
        _paths = new ArrayList<>(paths);
        _items = new ArrayList<>(items);
        _numItems = _items.size();
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
            if(i.isAnimated() || !i.isTaken())
                i.update(deltaTime);
        }

        if(_enemies != null)
        {
            for (Enemy e: _enemies) {
                e.update(deltaTime);
            }
        }

        if(_numItems == 0){
            if(_elapsedTime > _timeWaitToNextLevel){
                _elapsedTime = 0;
                if(_logic.playerIsAlive())
                    _logic.setNextLevel();
            }
            _elapsedTime += deltaTime;
        }
    }

    void render(Graphics g){

        drawText(g);

        for (Path p: _paths) {
            p.render(g);
        }

        for (Item i: _items) {
            if(i.isAnimated() || !i.isTaken())
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

    void resetLevel(){
        _numItems = _items.size();
        _elapsedTime = 0;
        for (Item i: _items) {
            i.reset();
        }

        if(_enemies != null) {
            for (Enemy e : _enemies) {
                e.reset();
            }
        }

    }

    ArrayList<Path> getPaths(){ return _paths; }

    ArrayList<Item> getItems(){ return _items; }

    int getNumItems(){ return _numItems; }

    void takeItem(){
        _numItems--;
    }

    void setLogic(Game l){
        _logic = l;
    }

    ArrayList<Enemy> getEnemies(){ return _enemies; }

    void init(){
        resetLevel();
    }
}
