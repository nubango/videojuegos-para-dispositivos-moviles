package es.ucm.gdv.offtheline;

import java.awt.Color;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {
    Font _f;
    Engine _engine;

    OffTheLineLogic(){

    }

    @Override
    public boolean init(Engine e) {
        _engine = e;
        //_f = _engine.getGraphics().newFont("Bangers-Regular.ttf", 80, true);
        return true;
    }

    public void update(double deltaTime)
    {

    };

    public void render(Graphics g)
    {

        g.clear(0xFFFFFFFF);

        g.setColor(0xFF123456);

        //g.setFont(_f);
        //g.drawText("texto de prueba", 250,150);

        if(g.save()) {
            g.scale(2, 2);
        }
        g.fillRect(0,0, 100, 100);
        g.drawLine(0,0, g.getWidth(), g.getHeight());

        g.restore();

    };
}