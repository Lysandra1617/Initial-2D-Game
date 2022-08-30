package Listeners;

import Central.Main;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Screen extends ComponentAdapter {
    int screenWidth = 0;
    int screenHeight = 0;

    @Override
    public void componentResized(ComponentEvent e) {
        screenWidth = e.getComponent().getWidth();
        screenHeight = e.getComponent().getHeight();
        Main.INTERFACE.screenResize();
    }

    public int screenWidth() {
        return screenWidth;
    }

    public int screenHeight() {
        return screenHeight;
    }
}
