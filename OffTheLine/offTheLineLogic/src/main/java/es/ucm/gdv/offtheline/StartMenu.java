package es.ucm.gdv.offtheline;

import java.io.FileNotFoundException;
import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class StartMenu implements Logic {

    private OffTheLineLogic _otl;

    private Font _titleFont;
    private Font _subtitleFont;
    private Font _easyModeTitlefont;
    private Font _easyModeSubtitlefont;
    private Font _hardModeTitlefont;
    private Font _hardModeSubtitlefont;

    private Engine _engine;

    private boolean _goToGame = false;
    private boolean _easy = true;

    StartMenu(OffTheLineLogic otl){
        _otl = otl;
    }
    @Override
    public boolean init(Engine engine) {
        _engine = engine;

        try {
            _titleFont = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf",
                    45, true);
            _subtitleFont = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf",
                    25, true);
            _easyModeTitlefont = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf",
                    33, false);
            _easyModeSubtitlefont = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf",
                    19, false);
            _hardModeTitlefont = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf",
                    33, false);
            _hardModeSubtitlefont = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf",
                    19, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    Utils.Point p;

    public void handleInput() {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();

        if(e == null)
            return;
        p = null;
        for (Input.TouchEvent ev:e) {
            if(ev._type == Input.Type.press){
                p = new Utils.Point(ev._x, ev._y);
                if(ev._x > 60 && ev._x < 265 && ev._y > 260 && ev._y < 305) {
                    _goToGame = true;
                    _easy = true;
                }
                else if(ev._x > 60 && ev._x < 265 && ev._y > 260 && ev._y < 345) {
                    _goToGame = true;
                    _easy = false;
                }

            }
        }
    }

    @Override
    public void update(double deltaTime) {
        handleInput();
    }

    @Override
    public void render(Graphics g) {
        g.clear(0xFF000000);

        g.setColor(0xFF0081F9);
        g.setFont(_titleFont);
        g.drawText("off the line" ,60, 100);

        g.setFont(_subtitleFont);
        g.drawText("a game copied to bryan perfletto" ,60, 140);

        g.setColor(0xFFFFFFFF);
        g.setFont(_easyModeTitlefont);
        g.drawText("easy mode" ,60, 300);
        g.setFont(_hardModeTitlefont);
        g.drawText("hard mode" ,60, 340);

        g.setColor(0xFF9B9B9B);
        g.setFont(_easyModeSubtitlefont);
        g.drawText("(slow speed, 10 levels)" ,265, 300);
        g.setFont(_hardModeSubtitlefont);
        g.drawText("(fast speed, 5 levels)" ,270, 340);

        endOfLoop();
    }

    private void endOfLoop(){
        if(_goToGame) {
            _otl.goToGame(_easy);
            _goToGame = false;
        }
    }
}
