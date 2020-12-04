package es.ucm.gdv.offtheline;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.AbstractGraphics;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {
    Font _f;
    Engine _engine;
    Item it;
    Enemy enemy;

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


        return true;
    }
    double angle = 0;
    public void update(double deltaTime)
    {
        List<Input.TouchEvent> e = _engine.getInput().getTouchEvents();
        if (e != null)
            System.out.println("Dedo usado: " + e.get(0)._fingerId + " Tipo de pulsación: "
                    + e.get(0)._type + " Coordenadas x: " + e.get(0)._x +" y: " + e.get(0)._y);

        it.update(deltaTime);
        enemy.update(deltaTime);
    };

    public void render(Graphics g)
    {
        _engine.getGraphics().clear(0xFF000000);

        it.render(g);
        enemy.render(g);



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