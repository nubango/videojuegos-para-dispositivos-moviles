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
    Object _levels;

    @Override
    public boolean init(Engine e) {
        _engine = e;

        _engine.getGraphics().setLogicSize(640,480);
        try {
            _f = _engine.getGraphics().newFont("Bangers-Regular.ttf", 80, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        it = new Item(100,100);
        enemy = new Enemy(100,100,20,90,50,100,1,2);
        player = new Player(100, 200);

        path = new ArrayList<Path>();

        int width = _engine.getGraphics().getWidth()/2;
        int height = _engine.getGraphics().getHeight()/2;

        ArrayList<Utils.Point> vertexes = new ArrayList<Utils.Point>();

        vertexes.add(new Utils.Point(-100+width,100+height));
        vertexes.add(new Utils.Point(100+width,100+height));

        vertexes.add(new Utils.Point(100+width,-100+height));
        vertexes.add(new Utils.Point(-100+width,-100+height));
        path.add(new Path(vertexes, null));


        vertexes.clear();
        vertexes.add(new Utils.Point(-300+width,50+height));
        vertexes.add(new Utils.Point(-225+width,0+height));
        vertexes.add(new Utils.Point(-225+width,50+height));
        vertexes.add(new Utils.Point(225+width,50+height));
        vertexes.add(new Utils.Point(225+width,15+height));
        vertexes.add(new Utils.Point(300+width,0+height));
        vertexes.add(new Utils.Point(225+width,0+height));
        vertexes.add(new Utils.Point(225+width,-50+height));
        vertexes.add(new Utils.Point(-225+width,-50+height));
        vertexes.add(new Utils.Point(-225+width,0+height));
       // path.add(new Path(vertexes, null));


        player.setCurrentPath(path.get(0));

        // cargamos los niveles

        //JSONReader jsonReader = new JSONReader("assets/levels.json");

        return true;
    }

    public void handleInput() {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();
        /*if (e != null)
            System.out.println("Dedo usado: " + e.get(0)._fingerId + " Tipo de pulsación: "
                    + e.get(0)._type + " Coordenadas x: " + e.get(0)._x +" y: " + e.get(0)._y);
         */

        player.handleInput(e);

    }

    public void update(double deltaTime)
    {
        /*
        * leerLever() devuelve un objeto level que contiene todoo lo del nivel (enemigos, items, caminos,...)
        *
        * if (levelPasado)
        *   levelActualObject = leerLevel(++countLevel);
        *
        *  levelActualObject.update();
        * */




        it.update(deltaTime);
        enemy.update(deltaTime);
        player.update(deltaTime);
    };

    public void render(Graphics g)
    {
        /*
        * levelActualObject.render()
        * */


        _engine.getGraphics().clear(0xFF000000);

        it.render(g);
        enemy.render(g);
        player.render(g);

        for (Path p:path) {
            p.render(g);
        }

/*
        g.clear(0xFFFFFFFF);

        g.setColor(0xFF123456);


        if(g.save()) {
            g.scale(2, 2);
            g.rotate(10);
        }
        g.setFont(_f);
        g.drawText("texto de prueba", 250,150);
        g.fillRect(0,0, 100, 100);
        g.drawLine(0,0, g.getWidth(), g.getHeight());

        g.restore();*/


    };
}