package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.windows.Engine;

public class WindowsLauncher {
    public static void main(String[] args)
    {
        Engine e = new Engine();
        e.createWindow("test", 1920, 1080);
        OffTheLineLogic otl = new OffTheLineLogic(e);
        e.run();
    }
}