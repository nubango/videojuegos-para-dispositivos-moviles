package es.ucm.gdv.engine.windows;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Font implements es.ucm.gdv.engine.Font {
    Font(String filename, int size, boolean isBold, Engine engine) {

        java.awt.Font baseFont = null;
        try (InputStream is = engine.openInputStream(filename)){
            baseFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            System.err.println("Error cargando la fuente del fichero "+filename+": " + e);
        }

        // baseFont contiene el tipo de letra base en tamaño 1. La
        // usamos como punto de partida para crear la nuestra, más
        // grande y en negrita.
        if(isBold) _font = baseFont.deriveFont(java.awt.Font.BOLD, size);
        else _font = baseFont.deriveFont(java.awt.Font.PLAIN, size);

    }

    public java.awt.Font getFont() { return _font; }

    java.awt.Font _font;
}
