package Maps;

import Maps.Units.Item.Item;
import Maps.Units.Tile;
import Markers.Drawing;

import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class ItemMap implements Drawing, Serializable {
    @Serial
    private static final long serialVersionUID = 123L;

    static class Bundle implements Serializable {
        ArrayList<Item> bundle = new ArrayList<>();
    }

    Map parent;
    final ArrayList<Item> ITEMS;
    final Bundle[][] ITEM_MAP;
    public final int BASE_LAYER = 5;
    transient final Comparator<Item> ASC_Z_SORT = Comparator.comparingInt(Item::getZ);
    transient final Comparator<Item> DESC_Z_SORT = ASC_Z_SORT.reversed();
    int zGlobal;

    public ItemMap(Map m) {
        parent = m;
        ITEMS = new ArrayList<>();
        ITEM_MAP = new Bundle[parent.rows()][parent.columns()];
        zGlobal = 0;
    }

    public void setParent(Map m) {
        parent = m;
    }

    public ArrayList<Item> items() {
        return ITEMS;
    }

    public Map map() {
        return parent;
    }

    public void map(Item i) {
        for (int r = i.getStartRow(); r <= i.getEndRow(); r++) {
            for (int c = i.getStartCol(); c <= i.getEndCol(); c++) {
                if (ITEM_MAP[r][c] == null)
                    ITEM_MAP[r][c] = new Bundle();
                ITEM_MAP[r][c].bundle.add(i);
            }
        }
    }

    public void remap(Item i, int x, int y, int w, int h) {
        final int ROW_START_0 = Math.max(0, i.getStartRow());
        final int ROW_END_0 = Math.min(parent.rows() - 1, i.getEndRow());
        final int COL_START_0 = Math.max(0, i.getStartCol());
        final int COL_END_0 = Math.min(parent.columns() - 1, i.getEndCol());
        final int ROW_START_1 = Math.max(0, y / Tile.TILE_HEIGHT);
        final int ROW_END_1 = Math.min(parent.rows() - 1, (y + h) / Tile.TILE_HEIGHT);
        final int COL_START_1 = Math.max(0, x / Tile.TILE_WIDTH);
        final int COL_END_1 = Math.min(parent.columns() - 1, (x + w) / Tile.TILE_WIDTH);
        final int ROW_START = Math.min(ROW_START_0, ROW_START_1);
        final int ROW_END = Math.max(ROW_END_0, ROW_END_1);
        final int COL_START = Math.min(COL_START_0, COL_START_1);
        final int COL_END = Math.max(COL_END_0, COL_END_1);
        for (int r = ROW_START; r <= ROW_END; r++) {
            for (int c = COL_START; c <= COL_END; c++) {
                if (r < ROW_START_1 || r > ROW_END_1 || c < COL_START_1 || c > COL_END_1) {
                    if (ITEM_MAP[r][c] != null) ITEM_MAP[r][c].bundle.remove(i);
                }
                else {
                    if (ITEM_MAP[r][c] == null) ITEM_MAP[r][c] = new Bundle();
                    if (!ITEM_MAP[r][c].bundle.contains(i)) {
                        ITEM_MAP[r][c].bundle.add(i);
                    }
                }
            }
        }
    }

    public void add(int ID, int x, int y) {
        Item item = new Item(this, ID);
        item.setX(x);
        item.setY(y);
        item.setZ(zGlobal++);
        item.setScreenAdjustX(parent.screenAdjustX());
        item.setScreenAdjustY(parent.screenAdjustY());
        item.setMapAdjustX(parent.mapAdjustX());
        item.setMapAdjustY(parent.mapAdjustY());
        ITEMS.add(item);
        map(item);
    }

    public void delete(int pointX, int pointY) {
        delete(find(pointX, pointY, false));
    }

    public void delete(Item i) {
        remove(i);
        ITEMS.remove(i);
    }

    public Item find(int pointX, int pointY, boolean resizing) {
        final int ROW = parent.mapRow(pointY);
        final int COL = parent.mapCol(pointX);
        final int ROW_0 = Math.max(ROW - 1, 0);
        final int ROW_1 = Math.min(ROW + 1, parent.rows() - 1);
        final int COL_0 = Math.max(COL - 1, 0);
        final int COL_1 = Math.min(COL + 1, parent.columns() - 1);
        for (int r = ROW_0; r <= ROW_1; r++) {
            for (int c = COL_0; c <= COL_1; c++) {
                if (ITEM_MAP[r][c] != null) {
                    final ArrayList<Item> TEMP = ITEM_MAP[r][c].bundle;
                    TEMP.sort(DESC_Z_SORT);
                    for (Item i : TEMP) {
                        if (resizing) {
                            if (i.vertices().find(parent.mapX(pointX), parent.mapY(pointY)) != null) {
                                return i;
                            }
                        }
                        else if (i.contains(parent.mapX(pointX), parent.mapY(pointY))) {
                            return i;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void remove(Item i) {
        for (int r = i.getStartRow(); r <= i.getEndRow(); r++) {
            for (int c = i.getStartCol(); c <= i.getEndCol(); c++) {
                ITEM_MAP[r][c].bundle.remove(i);
            }
        }
    }

    public void adjustOrder(Item i, boolean direction) {
        int index = ITEMS.indexOf(i);
        if (index < 0) return;
        int change = (direction) ? 1 : -1;
        if (index == 0 && change < 0 || index == ITEMS.size() - 1 && change > 0) return;
        i.addZ(change);
        Item swapBuddy = ITEMS.get(index + change);
        swapBuddy.addZ(-change);
        ITEMS.set(index, swapBuddy);
        ITEMS.set(index + change, i);
    }

    @Override
    public void draw(Graphics2D G) {
        for (Item i : ITEMS) {
            i.draw(G);
        }
    }
}
