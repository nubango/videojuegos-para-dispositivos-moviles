package es.ucm.gdv.engine;

/*
 * Interfaz para poder abstraer el bucle principal del juego de la lógica
 */

import java.io.IOException;

public interface Logic {
    boolean init(Engine engine);
    void update(double deltaTime);
    void render(Graphics g);

}
