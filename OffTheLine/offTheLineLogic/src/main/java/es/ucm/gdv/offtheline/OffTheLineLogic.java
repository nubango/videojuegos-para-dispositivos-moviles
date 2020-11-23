package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Logic;

public class OffTheLineLogic implements Logic {

    public void update(double deltaTime)
    {

    };
    public void render(Graphics g)
    {
        g.drawLine(1,1,500,500);
    };
}