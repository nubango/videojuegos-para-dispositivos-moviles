package es.ucm.gdv.engine.windows;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Font implements es.ucm.gdv.engine.Font {

    private java.awt.Font _font;

    Font(String filename, int size, boolean isBold, Engine engine) throws FileNotFoundException {

        java.awt.Font baseFont = null;
        InputStream is = engine.openInputStream(filename);
        
        try {
            baseFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // baseFont contiene el tipo de letra base en tamaño 1. La
        // usamos como punto de partida para crear la nuestra, más
        // grande y en negrita.
        if(isBold) set_font(baseFont.deriveFont(java.awt.Font.BOLD, size));
        else set_font(baseFont.deriveFont(java.awt.Font.PLAIN, size));

    }

/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    public java.awt.Font getFont() { return get_font(); }

    public java.awt.Font get_font() {
        return _font;
    }

    public void set_font(java.awt.Font _font) {
        this._font = _font;
    }
}
