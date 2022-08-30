package Interface.Components.Buttons;

import Utilities.UT_Draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageButton extends Button {
    BufferedImage image;

    public ImageButton() {
        image = null;
    }

    public void setImage(BufferedImage b) {
        image = b;
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawImage(x, y, w, h, image, G);
    }
}
