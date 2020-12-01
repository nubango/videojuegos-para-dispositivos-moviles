package es.ucm.gdv.offtheline;

import java.awt.Color;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {

    public void update(double deltaTime)
    {

    };
    public void render(Graphics g)
    {

        //g.clear(0xFFFFFFFF);

        g.setColor(0xff4cab00);
        if(g.save()) {
            g.scale(2, 2);
        }
        g.fillRect(0,0, 100, 100);
        g.drawLine(0,0, g.getWidth(), g.getHeight());

        g.restore();

    };
}