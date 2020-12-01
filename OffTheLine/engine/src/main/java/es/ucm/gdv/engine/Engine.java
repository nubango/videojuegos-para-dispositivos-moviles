package es.ucm.gdv.engine;

import java.io.FileNotFoundException;
import java.io.InputStream;

/*
 * (Graphics e Input serian singleton si Java lo permitiese tal cual)
 * Encargado de mantener las instancias y otros metodos utiles de acceso a la plataforma
 */

public interface Engine {

    // Devuelve la instancia del motor grafico
    Graphics getGraphics();

    // Devuelve la instancia del gestor de entrada
    Input getInput();

    // Devuelve un stream de lectura de un fichero
    InputStream openInputStream(String filename) throws FileNotFoundException;
}
