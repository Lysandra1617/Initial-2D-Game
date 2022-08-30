package Maps;

import Maps.Units.Tile;
import Markers.Drawing;

import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;

public class TileMap implements Drawing, Serializable {
    @Serial
    private static final long serialVersionUID = 12L;
    Map parent;
    final Tile[][] TILES; //All the tiles in the tile map

    public TileMap(Map map) {
        parent = map;
        TILES = new Tile[parent.rows()][parent.columns()];
    }

    public void setParent(Map m) {
        parent = m;
    }

    public Tile[][] tiles() {
        return TILES;
    }

    public void add(int ID, int row, int col) {
        TILES[row][col] = new Tile(ID);
        TILES[row][col].setRow(row);
        TILES[row][col].setColumn(col);
        TILES[row][col].setScreenAdjustX(parent.screenAdjustX());
        TILES[row][col].setScreenAdjustY(parent.screenAdjustY());
        TILES[row][col].setMapAdjustX(parent.mapAdjustX());
        TILES[row][col].setMapAdjustY(parent.mapAdjustY());
    }

    public void delete(int row, int col) {
        TILES[row][col] = null;
    }

    @Override
    public void draw(Graphics2D G) {
        for (Tile[] tiles : TILES) {
            for (Tile tile : tiles) {
                if (tile != null) tile.draw(G);
            }
        }
    }
}
