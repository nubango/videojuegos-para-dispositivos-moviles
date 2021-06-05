package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class StartMenu implements Logic {

    private OffTheLineLogic _otl;
    private Font _font;
    private Engine _engine;

    private boolean _goToGame = false;

    StartMenu(OffTheLineLogic otl, Font f){
        _otl = otl;
        _font = f;
    }
    @Override
    public boolean init(Engine engine) {
        _engine = engine;
        return true;
    }

    public void handleInput() {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();

        if(e == null)
            return;

        for (Input.TouchEvent ev:e) {
            if(ev._type == Input.Type.press){
                _goToGame = true;
            }
        }

    }

    @Override
    public void update(double deltaTime) {
        handleInput();
    }

    @Override
    public void render(Graphics g) {

        endOfLoop();
    }

    private void endOfLoop(){
        if(_goToGame) {
            _otl.goToGame();
            _goToGame = false;
        }
    }
}
