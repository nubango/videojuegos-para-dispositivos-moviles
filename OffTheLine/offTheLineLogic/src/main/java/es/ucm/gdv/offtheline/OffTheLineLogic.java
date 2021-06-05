package es.ucm.gdv.offtheline;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic {
    private Font _font;
    private Engine _engine;
    private StartMenu _menu;
    private Game _game;

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    public OffTheLineLogic(Engine e) {
        _engine = e;

        _engine.getGraphics().setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);
        try {
            _font = _engine.getGraphics().newFont("fonts/BungeeHairline-Regular.ttf",
                    10, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        _menu = new StartMenu(this, _font);
        _game = new Game(this, _font);

        _engine.setLogic(_menu);
    }

    void goToMenu(){
        _engine.setLogic(_menu);
    }

    void goToGame(){
        _engine.setLogic(_game);
    }
}