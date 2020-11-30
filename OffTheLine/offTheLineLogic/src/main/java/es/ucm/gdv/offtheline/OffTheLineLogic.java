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
        g.clear(0xFF0000FF);

        g.setColor(0x00000000);
        if(g.save()) {
            g.scale(2, 2);
        }
        g.drawLine(0,0, g.getWidth()-1, g.getHeight()-1);

        g.restore();



    };
}