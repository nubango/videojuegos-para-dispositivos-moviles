package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.windows.Engine;

public class WindowsLauncher {
    public static void main(String[] args)
    {
        System.out.println("Hola mundo!");

        Engine e = new Engine();
        e.init("test");

    }
}