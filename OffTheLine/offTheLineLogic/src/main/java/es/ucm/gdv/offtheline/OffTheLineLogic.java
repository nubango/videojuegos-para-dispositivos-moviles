package es.ucm.gdv.offtheline;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {
    private Font _font;
    private Engine _engine;
    private Player _player;
    private ArrayList<Level> _levels;
    private int _currentLevel = 5;

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    @Override
    public boolean init(Engine e) {
        _engine = e;

        _engine.getGraphics().setLogicSize(LOGIC_WIDTH,LOGIC_HEIGHT);
        try {
            _font = _engine.getGraphics().newFont("fonts/BungeeHairline-Regular.ttf", 10, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        JSONReader jsonReader = new JSONReader(_engine);
        _levels = jsonReader.parserLevels("levels.json");

        _player = new Player(100, 200);
        _player.setCurrentLevel(getLevels().get(getCurrentLevel()));
        _player.setCurrentPath(getLevels().get(getCurrentLevel())._paths.get(0));

        return true;
    }

    public void handleInput() {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();

        if(e == null)
            return;

        _player.handleInput(e);
    }

    public void update(double deltaTime) {
        _levels.get(getCurrentLevel()).update(deltaTime);

        _player.update(deltaTime);
    };

    public void render(Graphics g) {
        _engine.getGraphics().clear(0xFF000000);

        _levels.get(getCurrentLevel()).render(g);

        _player.render(g);

        _engine.getGraphics().setColor(0xFFFFFFFF);
        g.save();
        g.translate(0, 0);
        int level = _currentLevel +1;
        g.setFont(_font);
        g.drawText("Level " + level + " - " + _levels.get(_currentLevel)._name,100, 50);
        g.restore();
    };

    public Font getFont() {
        return _font;
    }

    public void setFont(Font font) {
        _font = font;
    }

    public Engine getEngine() {
        return _engine;
    }

    public void setEngine(Engine engine) {
        _engine = engine;
    }

    public Player getPlayer() {
        return _player;
    }

    public void setPlayer(Player player) {
        this._player = player;
    }

    public ArrayList<Level> getLevels() {
        return _levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        _levels = levels;
    }

    public int getCurrentLevel() {
        return _currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        _currentLevel = currentLevel;
    }
}