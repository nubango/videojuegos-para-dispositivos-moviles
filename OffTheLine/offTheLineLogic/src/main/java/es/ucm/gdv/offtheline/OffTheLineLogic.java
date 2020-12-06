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
    private Font _f;
    private Engine _engine;
    private Player player;
    private ArrayList<Level> levels;
    private int currentLevel = 5;

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    @Override
    public boolean init(Engine e) {
        set_engine(e);

        get_engine().getGraphics().setLogicSize(LOGIC_WIDTH,LOGIC_HEIGHT);
        try {
            set_f(get_engine().getGraphics().newFont("fonts/BungeeHairline-Regular.ttf", 10, true));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        JSONReader jsonReader = new JSONReader(get_engine());
        setLevels(jsonReader.parserLevels("levels.json"));

        setPlayer(new Player(100, 200));
        getPlayer().setCurrentLevel(getLevels().get(getCurrentLevel()));
        getPlayer().setCurrentPath(getLevels().get(getCurrentLevel())._paths.get(0));

        return true;
    }

    public void handleInput() {
        List<Input.TouchEvent> e = get_engine().getInput().getTouchEvents();

        if(e == null)
            return;

        getPlayer().handleInput(e);
    }

    public void update(double deltaTime) {
        getLevels().get(getCurrentLevel()).update(deltaTime);

        getPlayer().update(deltaTime);
    };

    public void render(Graphics g) {
        get_engine().getGraphics().clear(0xFF000000);

        getLevels().get(getCurrentLevel()).render(g);

        getPlayer().render(g);

        get_engine().getGraphics().setColor(0xFFFFFFFF);
        g.save();
        g.translate(0, 0);
        int level = currentLevel+1;
        g.setFont(_f);
        g.drawText("Level " + level + " - " + levels.get(currentLevel)._name,100, 50);
        g.restore();
    };

    public Font get_f() {
        return _f;
    }

    public void set_f(Font _f) {
        this._f = _f;
    }

    public Engine get_engine() {
        return _engine;
    }

    public void set_engine(Engine _engine) {
        this._engine = _engine;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}