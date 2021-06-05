package es.ucm.gdv.offtheline;


import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;

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

        _menu = new StartMenu(this);
        _game = new Game(this);

        _engine.setLogic(_menu);
    }

    void goToMenu(){
        _engine.setLogic(_menu);
    }

    void goToGame(boolean easy){
        _game.setDificulty(easy);
        _engine.setLogic(_game);
    }
}