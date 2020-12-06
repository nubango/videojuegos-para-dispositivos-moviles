package es.ucm.gdv.offtheline;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {
    Font _f;
    Engine _engine;
    Item it;
    Enemy enemy;
    Player player;
    ArrayList<Path> path;
    ArrayList<Level> levels;
    int currentLevel = 5;

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    @Override
    public boolean init(Engine e) {
        _engine = e;

        _engine.getGraphics().setLogicSize(LOGIC_WIDTH,LOGIC_HEIGHT);
        try {
            _f = _engine.getGraphics().newFont("fonts/Bungee-Regular.ttf", 80, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        JSONReader jsonReader = new JSONReader(_engine);
        levels = jsonReader.parserLevels("levels.json");

        player = new Player(100, 200);
        player.setCurrentLevel(levels.get(currentLevel));
        player.setCurrentPath(levels.get(currentLevel)._paths.get(0));

        return true;
    }

    public void handleInput() {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();

        player.handleInput(e);
    }

    public void update(double deltaTime)
    {
        levels.get(currentLevel).update(deltaTime);

        player.update(deltaTime);
    };

    public void render(Graphics g)
    {
        _engine.getGraphics().clear(0xFF000000);

        levels.get(currentLevel).render(g);

        player.render(g);
    };
}