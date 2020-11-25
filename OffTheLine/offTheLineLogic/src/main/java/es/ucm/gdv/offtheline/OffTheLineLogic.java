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
        g.setColor(Color.blue);

        g.drawLine(0,0, g.getWidth(), g.getHeight());

        //g.fillRect(0, 0, 500, 100);  // 58 el lo que ocupa de alto la barra superior de la ventana

    };
}