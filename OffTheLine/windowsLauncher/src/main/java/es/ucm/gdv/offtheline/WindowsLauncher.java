package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.windows.Engine;

public class WindowsLauncher {
    public static void main(String[] args)
    {
        Engine e = new Engine();
        e.initApplication("test", 1000, 500);
        OffTheLineLogic logic = new OffTheLineLogic();
        e.run(logic);
    }
}