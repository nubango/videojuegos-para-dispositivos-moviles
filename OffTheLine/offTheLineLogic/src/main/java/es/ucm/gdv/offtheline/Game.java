package es.ucm.gdv.offtheline;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class Game implements Logic {
    private OffTheLineLogic _otl;
    private Font _font;
    private Engine _engine;
    private Player _player;
    private ArrayList<Level> _levels;
    private int _currentLevel = 0;
    private boolean _endOfGame = false;
    private boolean _easy = true;

    Game(OffTheLineLogic otl){
        _otl = otl;
    }

    @Override
    public boolean init(Engine e) {
        _engine = e;

        try {
            _font = _engine.getGraphics().newFont("fonts/BungeeHairline-Regular.ttf",
                    10, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        JSONReader jsonReader = new JSONReader(_engine);
        _levels = jsonReader.parserLevels("levels.json");

        _player = new Player();

        _levels.get(_currentLevel).setLogic(this);
        _player.setCurrentLevel(_levels.get(_currentLevel));
        _player.setDificulty(_easy);
        _levels.get(_currentLevel).setFont(_font);

        return true;
    }

    void setNextLevel() {
        if (_currentLevel < 19) {
            _currentLevel = (_currentLevel + 1) % _levels.size();
            _levels.get(_currentLevel).setLogic(this);
            _levels.get(_currentLevel).init();
            _player.setCurrentLevel(_levels.get(_currentLevel));
            _levels.get(_currentLevel).setFont(_font);
        }else
            _endOfGame = true;
    }
    boolean playerIsAlive(){
        return !_player.isDeath();
    }

    void setDificulty(boolean easy){
        _easy = easy;
    }

    public void handleInput() {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();

        if(e == null)
            return;

        _player.handleInput(e);
    }

    public void update(double deltaTime) {
        // ESTO NO VA AQUI, EL HANDLEINPUT TIENE QUE DESAPARECER
        handleInput();
        // ESTO NO VA AQUI, EL HANDLEINPUT TIENE QUE DESAPARECER

        _levels.get(_currentLevel).update(deltaTime);

        _player.update(deltaTime);
    };

    public void render(Graphics g) {
        g.clear(0xFF000000);

        _levels.get(_currentLevel).render(g);

        _player.render(g);

        endOfLoop();
    };

    private void endOfLoop(){
        if(_endOfGame || _player.getCurrentLifes() == 0) {
            _otl.goToMenu();
            _player.reset();
            _levels.get(_currentLevel).init();
            _currentLevel = 0;
            _endOfGame = false;
        }
    }
}