package es.ucm.gdv.engine;

/*
 * Proporciona las funcionalidades graficas minimas sobre la ventana de la aplicacion
 */


public interface Graphics {

    // Crea una nueva fuente del tamano especificado a partir de un fichero .ttf
    // Se indica si se desea o no la fuente en negrita
     Font newFont(String filename, int size, boolean isBold);

    // Borra el contenido completo de la ventana, rellenandolo con un color recibido
    void clear(int color);

    // Metodos de control de la transformacion sobre el canvas
    void translate(int x, int y);
    void scale(double x, double y);
    void rotate(double angle);
    boolean save();
    void restore();

    // Establece el color a utilizar en las operaciones de dibujado posteriores
    void setColor(int color);

    // Dibuja una linea
    void drawLine(int x1, int y1, int x2, int y2);

    // Dibuja un rectangulo relleno
    void fillRect(int x1, int y1, int x2, int y2);

    // Escribe el texto con la fuente y colores activos
    void drawText(String text, int x, int y);

    // Devuelven el tamano de la ventana
    int getWidth();
    int getHeight();
    void setLogicSize(int w, int h);

}
