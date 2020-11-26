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
        g.clear(Color.white);

        g.setColor(Color.blue.darker());
        if(g.save()) {
            g.scale(2, 2);
        }
        g.drawLine(0,0, g.getWidth(), g.getHeight());

        g.restore();

    };
}