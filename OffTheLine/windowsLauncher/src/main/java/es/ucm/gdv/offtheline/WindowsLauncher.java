package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.windows.Engine;

public class WindowsLauncher {
    public static void main(String[] args)
    {
        Engine e = new Engine();
        e.init("test");
        OffTheLineLogic logic = new OffTheLineLogic();
        e.initLogic(logic);
    }
}