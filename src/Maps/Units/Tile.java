package Maps.Units;

import Central.Main;
import Utilities.UT_Draw;

import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;

public class Tile extends MapUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 21L;

    public final static int TILE_WIDTH = 48;
    public final static int TILE_HEIGHT = 48;
    int row;
    int col;

    public Tile(int id) {
        ID = id;
        w = TILE_WIDTH;
        h = TILE_HEIGHT;
        image = Main.MAP_IMAGE_MANAGER.TILE_IMAGES[id];
    }

    public void setRow(int r) {
        row = r;
        y = row * TILE_HEIGHT;
    }

    public void setColumn(int c) {
        col = c;
        x = col * TILE_WIDTH;
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawImage(x+screenAdjustX+mapAdjustX,y+screenAdjustY+mapAdjustY,TILE_WIDTH,TILE_HEIGHT,image,G)  ;
    }

    @Override
    public void init() {
        assert Main.MAP_IMAGE_MANAGER.TILE_IMAGES != null;
        image = Main.MAP_IMAGE_MANAGER.TILE_IMAGES[ID];
    }
}
