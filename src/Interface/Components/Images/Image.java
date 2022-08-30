package Interface.Components.Images;

import Markers.Area;
import Markers.Drawing;
import Utilities.UT_Draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Image extends Area implements Drawing {
    //This is just the encapsulation of the BufferedImage
    BufferedImage image;

    public Image(BufferedImage b) {
        image = b;
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawImage(x, y, w, h, image, G);
    }
}
