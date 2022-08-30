package Maps.Units;

import Markers.Area;
import Markers.Drawing;
import Markers.Stored;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

public abstract class MapUnit extends Area implements Drawing, Serializable, Stored {
    @Serial
    private static final long serialVersionUID = 2L;
    protected int ID;
    transient protected BufferedImage image;
    transient protected int screenAdjustX;
    transient protected int screenAdjustY;
    transient protected int mapAdjustX;
    transient protected int mapAdjustY;

    public void setID(int id) {
        ID = id;
    }

    public void setImage(BufferedImage img) {
        image = img;
    }

    public void setScreenAdjustX(int i) {
        screenAdjustX = i;
    }

    public void setScreenAdjustY(int i) {
        screenAdjustY = i;
    }

    public void setMapAdjustX(int i) {
        mapAdjustX = i;
    }

    public void setMapAdjustY(int i) {
        mapAdjustY = i;
    }

    public int getID() {
        return ID;
    }

    public BufferedImage getImage() {
        return image;
    }

    public static boolean inScope(MapUnit m, int mapLeft, int mapRight, int mapTop, int mapBottom) {
        int unitLeft = m.left();
        int unitRight = m.right();
        int unitTop = m.top();
        int unitBottom = m.bottom();
        boolean plainView = unitLeft > mapLeft && unitRight < mapRight && unitTop > mapTop && unitBottom < mapBottom;
        boolean obscuredView = mapLeft > unitLeft && mapLeft < unitRight || mapRight > unitLeft && mapRight < unitRight || mapBottom > unitTop && mapBottom < unitBottom || mapTop > unitTop && mapTop < unitBottom;
        return plainView || obscuredView;
    }
}
