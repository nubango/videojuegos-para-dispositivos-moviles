package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.windows.Engine;

public class WindowsLauncher {
    public static void main(String[] args)
    {
        Engine e = new Engine();
        e.createWindow("test", 1080, 1500);
        OffTheLineLogic logic = new OffTheLineLogic("Bangers-Regular.ttf", e);
        e.run(logic);
    }
}