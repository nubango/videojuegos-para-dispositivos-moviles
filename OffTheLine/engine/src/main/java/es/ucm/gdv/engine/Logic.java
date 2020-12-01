package es.ucm.gdv.engine;

/*
 * Interfaz para poder abstraer el bucle principal del juego de la lógica
 */

public interface Logic {
    boolean init();
    void update(double deltaTime);
    void render(Graphics g);

}
