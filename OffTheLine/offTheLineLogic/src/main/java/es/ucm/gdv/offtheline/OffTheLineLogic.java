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
    Font _f;
    Engine _engine;
    Item it;
    Enemy enemy;
    Player player;
    Path path;

    @Override
    public boolean init(Engine e) {
        _engine = e;

        try {
            _f = _engine.getGraphics().newFont("Bangers-Regular.ttf", 80, true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        it = new Item(100,100);
        enemy = new Enemy(200,100,20,90,50,100,1,2);
        player = new Player(100, 200);

        ArrayList<Integer> vertexes = new ArrayList<Integer>();

        vertexes.add(-100);
        vertexes.add(100);
        vertexes.add(100);
        vertexes.add(100);

        vertexes.add(100);
        vertexes.add(-100);
        vertexes.add(-100);
        vertexes.add(-100);


        path = new Path(vertexes);

        return true;
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

        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();
        if (e != null)
            System.out.println("Dedo usado: " + e.get(0)._fingerId + " Tipo de pulsación: "
                    + e.get(0)._type + " Coordenadas x: " + e.get(0)._x +" y: " + e.get(0)._y);

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
        path.render(g);



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