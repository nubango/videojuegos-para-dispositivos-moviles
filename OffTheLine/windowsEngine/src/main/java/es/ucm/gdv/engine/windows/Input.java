package es.ucm.gdv.engine.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class Input extends es.ucm.gdv.engine.AbstractInput implements MouseListener, MouseMotionListener {

    Input(JFrame jFrame){
        jFrame.addMouseListener(this);
        jFrame.addMouseMotionListener(this);
    }

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
        _touchEvents.add(new TouchEvent(Type.press, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    /**
     * La acción de pulsar se ejecuta al sontar
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        _touchEvents.add(new TouchEvent(Type.release, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * Arrastrar el ratón con el botón pulsado
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        _touchEvents.add(new TouchEvent(Type.displace, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton()));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
