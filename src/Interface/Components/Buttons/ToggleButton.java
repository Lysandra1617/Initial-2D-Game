package Interface.Components.Buttons;

import Central.Main;
import Utilities.UT_Draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ToggleButton extends Button {
    final BufferedImage IMAGE_ON;
    final BufferedImage IMAGE_OFF;

    public ToggleButton() {
        IMAGE_ON = Main.G_IMAGE_MANAGER.TOGGLE_ON;
        IMAGE_OFF = Main.G_IMAGE_MANAGER.TOGGLE_OFF;
        w = 25;
        h = 20;
    }

    @Override
    public void mouseClick() {
        if (contains(Main.WINDOW.MOUSE.clickX(), Main.WINDOW.MOUSE.clickY())) {
            on = !on;
            clicked = true;
            clickTimer.restart();
        }
    }

    @Override
    public void draw(Graphics2D G) {
        if (on)
            UT_Draw.drawImage(x, y, w, h, IMAGE_ON, G);
        else
            UT_Draw.drawImage(x, y, w, h, IMAGE_OFF, G);
    }
}
