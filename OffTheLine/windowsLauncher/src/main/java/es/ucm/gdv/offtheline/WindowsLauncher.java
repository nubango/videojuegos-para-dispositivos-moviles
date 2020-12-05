package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.windows.Engine;

public class WindowsLauncher {
    public static void main(String[] args)
    {
        Engine e = new Engine();
        e.createWindow("test", 2080, 1500);
        OffTheLineLogic logic = new OffTheLineLogic();
        e.run(logic);
    }
}