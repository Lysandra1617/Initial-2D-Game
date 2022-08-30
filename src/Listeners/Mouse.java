package Listeners;

import Central.Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    int clickX = 0, clickY = 0;
    int hoverX = 0, hoverY = 0;
    int dragX1 = 0, dragY1 = 0;
    int dragX2 = 0, dragY2 = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        Main.INTERFACE.mouseClick();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragX1 = e.getX();
        dragY1 = e.getY();
        Main.INTERFACE.mousePress();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Main.INTERFACE.mouseRelease();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragX2 = e.getX();
        dragY2 = e.getY();
        Main.INTERFACE.mouseDrag();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hoverX = e.getX();
        hoverY = e.getY();
        Main.INTERFACE.mouseMove();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public int clickX() {
        return clickX;
    }

    public int clickY() {
        return clickY;
    }

    public int hoverX() {
        return hoverX;
    }

    public int hoverY() {
        return hoverY;
    }

    public int dragX1() {
        return dragX1;
    }

    public int dragY1() {
        return dragY1;
    }

    public int dragX2() {
        return dragX2;
    }

    public int dragY2() {
        return dragY2;
    }
}
