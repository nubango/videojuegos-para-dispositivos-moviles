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
    private int _currentLevel = 4;

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    @Override
    public boolean init(Engine e) {
        _engine = e;

        _engine.getGraphics().setLogicSize(LOGIC_WIDTH,LOGIC_HEIGHT);
        try {
            _font = _engine.getGraphics().newFont("fonts/BungeeHairline-Regular.ttf",
                    10, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        JSONReader jsonReader = new JSONReader(_engine);
        _levels = jsonReader.parserLevels("levels.json");

        _player = new Player();
        _player.setCurrentPath(_levels.get(_currentLevel)._paths.get(0));
        _levels.get(_currentLevel).setFont(_font);

        return true;
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



    };
}