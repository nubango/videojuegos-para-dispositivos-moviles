package es.ucm.gdv.engine.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class Input extends es.ucm.gdv.engine.AbstractInput {

    private ClickEvents _clickEvents;
    private MotionEvents _motionEvents;
    private Graphics _graphics;

    Input(JFrame jFrame, Graphics e){

        _clickEvents = new ClickEvents();
        _motionEvents = new MotionEvents();
        _graphics = e;

        jFrame.addMouseListener(_clickEvents);
        jFrame.addMouseMotionListener(_motionEvents);
    }

    class MotionEvents implements MouseMotionListener {

        /* ---------------------------------------------------------------------------------------------- *
         * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
         * ---------------------------------------------------------------------------------------------- */

        /**
         * Arrastrar el ratón con el botón pulsado
         */
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            addEvent(new Input.TouchEvent(Input.Type.displace, (int)((mouseEvent.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor()),
                    (int)((mouseEvent.getY() - _graphics.getHeightBlackBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }

    class ClickEvents implements MouseListener {

        /* ---------------------------------------------------------------------------------------------- *
         * -------------------------------------- MÉTODOS PUBLICOS -------------------------------------- *
         * ---------------------------------------------------------------------------------------------- */

        /**
         * Si pulsas un botón, arrastras fuera del botón y sueltas no se ejecutara el click()
         * */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        /**
         * mouseEvent.getButton() => NOBUTTON (0) - sin pulsación, BUTTON1 (1) - derecho,
         *                           BUTTON2 (2) - centro, BUTTON3 (3) - izquierdo
         * */
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            addEvent(new Input.TouchEvent(Input.Type.press, (int)((mouseEvent.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor()),
                    (int)((mouseEvent.getY() - _graphics.getHeightBlackBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        /**
         * La acción de pulsar se ejecuta al sontar
         */
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            addEvent(new Input.TouchEvent(Input.Type.release, (int)((mouseEvent.getX() - _graphics.getWidthBlackBar()) / _graphics.getScaleFactor()),
                    (int)((mouseEvent.getY() - _graphics.getHeightBlackBar() - _graphics.hightBarOffset) / _graphics.getScaleFactor()), mouseEvent.getButton()));
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}
