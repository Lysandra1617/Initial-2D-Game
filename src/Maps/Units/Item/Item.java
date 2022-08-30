package Maps.Units.Item;

import Central.Main;
import Maps.ItemMap;
import Maps.Units.MapUnit;
import Maps.Units.Tile;

import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;

import static Utilities.UT_Draw.drawImage;

public class Item extends MapUnit implements Serializable {
    @Serial
    private static final long serialVersionUID = 212L;
    final int DEFAULT_WIDTH = 50;
    final int DEFAULT_HEIGHT = 50;
    int startRow;
    int startCol;
    int endRow;
    int endCol;
    transient Vertices vertices; //Items can be resized and moved around on the map.
    final ItemMap PARENT; //To update the mapping of items, item needs access to parent
    boolean edges;
    int z;

    public Item(ItemMap parent, int id) {
        PARENT = parent;
        ID = id;
        image = Main.MAP_IMAGE_MANAGER.ITEM_IMAGES[ID];
        w = DEFAULT_WIDTH;
        h = DEFAULT_HEIGHT;
        vertices = new Vertices(this);
    }

    @Override
    public void setID(int id) {
        ID = id;
        assert Main.MAP_IMAGE_MANAGER.ITEM_IMAGES != null;
        image = Main.MAP_IMAGE_MANAGER.ITEM_IMAGES[id];
    }

    @Override
    public void setX(int x) {
        PARENT.remap(this,x,y,w,h);
        this.x = x;
        startCol = x / Tile.TILE_WIDTH;
        endCol = (x + w) / Tile.TILE_WIDTH;
        vertices.init();
    }

    @Override
    public void setY(int y) {
        PARENT.remap(this,x,y,w,h);
        this.y = y;
        startRow = y / Tile.TILE_HEIGHT;
        endRow = (y + h) / Tile.TILE_WIDTH;
        vertices.init();
    }

    @Override
    public void setW(int w) {
        PARENT.remap(this,x,y,w,h);
        this.w = w;
        endCol = (x + w) / Tile.TILE_WIDTH;
        vertices.init();
    }

    @Override
    public void setH(int h) {
        PARENT.remap(this,x,y,w,h);
        this.h = h;
        endRow = (y + h) / Tile.TILE_HEIGHT;
        vertices.init();
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public int getZ() {
        return z;
    }

    public Vertices vertices() {
        return vertices;
    }

    public void addZ(int _z) {
        z += _z;
    }

    public void showEdges() {
        edges = true;
    }

    public void hideEdges() {
        edges = false;
    }

    @Override
    public void draw(Graphics2D G) {
        drawImage(x + screenAdjustX + mapAdjustX, y + screenAdjustY + mapAdjustY, w, h, image, G);
        if (edges) vertices.draw(G);
    }

    @Override
    public void init() {
        assert Main.MAP_IMAGE_MANAGER.TILE_IMAGES != null;
        image = Main.MAP_IMAGE_MANAGER.TILE_IMAGES[ID];
        vertices = new Vertices(this);
    }
}
