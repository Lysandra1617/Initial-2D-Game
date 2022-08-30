package Maps.Units.Item;

import Central.Main;
import Markers.Area;
import Markers.Drawing;
import Utilities.UT_Draw;

import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;

public class Vertex extends Area implements Drawing, Serializable {
    @Serial
    private static final long serialVersionUID = 2123L;
    final Vertices PARENT;

    Vertex(Vertices v) {
        PARENT = v;
        w = 10;
        h = 10;
    }

    @Override
    public boolean contains(int pointX, int pointY) {
        int cenX = x - w / 2;
        int cenY = y - h / 2;
        return pointX > cenX && pointX < (cenX + w) && pointY > cenY && pointY < (cenY + h);
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawImage(PARENT.PARENT.PARENT.map().mapAdjustX() + PARENT.PARENT.PARENT.map().screenAdjustX() + x - w / 2, PARENT.PARENT.PARENT.map().mapAdjustY() + PARENT.PARENT.PARENT.map().screenAdjustY() + y - h / 2, w, h, Main.G_IMAGE_MANAGER.TRANSP_BOX, G);
    }
}
