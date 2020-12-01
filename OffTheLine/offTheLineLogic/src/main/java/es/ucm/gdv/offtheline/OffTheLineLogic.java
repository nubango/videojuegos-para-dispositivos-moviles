package es.ucm.gdv.offtheline;

import java.awt.Color;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {
    Font _f;
    Engine _engine;

    OffTheLineLogic(String filenameFont, Engine e){
        _engine = e;
       _f = _engine.getGraphics().newFont(filenameFont, 40, true);
    }

    @Override
    public boolean init() {
        return true;
    }

    public void update(double deltaTime)
    {

    };

    public void render(Graphics g)
    {

        //g.clear(0xFFFFFFFF);

        g.setColor(0xff4cab00);

        g.setFont(_f);
        g.drawText("texto de prueba", 150,150);

        if(g.save()) {
            g.scale(2, 2);
        }
       // g.fillRect(0,0, 100, 100);
        g.drawLine(0,0, g.getWidth(), g.getHeight());

        g.restore();

    };
}