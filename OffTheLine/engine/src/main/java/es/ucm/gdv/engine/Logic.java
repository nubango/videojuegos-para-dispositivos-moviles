package es.ucm.gdv.engine;

/*
 * Interfaz para poder abstraer el bucle principal del juego de la lógica
 */

public interface Logic {

    void update(double deltaTime);
    void render(Graphics g);

}
