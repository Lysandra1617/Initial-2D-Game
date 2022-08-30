package Maps;

import Maps.Units.Item.Item;
import Maps.Units.Tile;
import Markers.Area;
import Markers.Drawing;
import Utilities.UT_Draw;
import Utilities.UT_File;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;
import java.util.Objects;

public class Map extends Area implements Drawing, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    String name;
    final int ROWS;
    final int COLUMNS;
    protected TileMap tilemap;
    protected ItemMap itemmap;
    transient int screenAdjustX; //This wasn't the best choice, but it's not that important. This is so the parts of a map will properly draw to the screen
    transient int screenAdjustY;
    transient int mapAdjustX; //Used to manipulate the view of the map
    transient int mapAdjustY;
    boolean grid;

    public Map() {
        ROWS = 100;
        COLUMNS = 100;
        w = COLUMNS * Tile.TILE_WIDTH;
        h = ROWS * Tile.TILE_HEIGHT;
        tilemap = new TileMap(this);
        itemmap = new ItemMap(this);
        name = "";
    }

    public Map(String name) {
        ROWS = 100;
        COLUMNS = 100;
        this.name = name;
        load();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        screenAdjustX = x;
        for (Tile[] tiles : tilemap.tiles()) {
            for (Tile tile : tiles) {
                if (tile != null) tile.setScreenAdjustX(x);
            }
        }
    }

    @Override
    public void setY(int y) {
        this.y = y;
        screenAdjustY = y;
        for (Tile[] tiles : tilemap.tiles()) {
            for (Tile tile : tiles) {
                if (tile != null) tile.setScreenAdjustY(y);
            }
        }
    }

    public void setMapAdjustX(int i) {
        mapAdjustX = i;
        for (Tile[] tiles : tilemap.tiles()) {
            for (Tile tile : tiles) {
                if (tile != null) tile.setMapAdjustX(i);
            }
        }
    }

    public void setMapAdjustY(int i) {
        mapAdjustY = i;
        for (Tile[] tiles : tilemap.tiles()) {
            for (Tile tile : tiles) {
                if (tile != null) tile.setMapAdjustY(i);
            }
        }
    }

    public void setName(String s) {
        if (UT_File.existingFile(path(s))) {
            System.out.println("File name taken. Pick a different one.");
            return;
        }
        name = s;
    }

    public String getName() {
        return name;
    }

    public int rows() {
        return ROWS;
    }

    public int columns() {
        return COLUMNS;
    }

    public TileMap tilemap() {
        return tilemap;
    }

    public ItemMap itemmap() {
        return itemmap;
    }

    public int screenAdjustX() {
        return screenAdjustX;
    }

    public int screenAdjustY() {
        return screenAdjustY;
    }

    public int mapAdjustX() {
        return mapAdjustX;
    }

    public int mapAdjustY() {
        return mapAdjustY;
    }

    public int mapRow(int mouseY) {
        int mapY = mapY(mouseY);
        if (mapY < 0) return -1;
        return mapY / Tile.TILE_HEIGHT;
    }

    public int mapCol(int mouseX) {
        int mapX = mapX(mouseX);
        if (mapX < 0) return -1;
        return mapX / Tile.TILE_WIDTH;
    }

    public int mapY(int mouseY) {
        return mouseY - y - mapAdjustY;
    }

    public int mapX(int mouseX) {
        return mouseX - x - mapAdjustX;
    }

    public void switchGrid() {
        grid = !grid;
    }

    public boolean save() {
        if (name.isEmpty()) {
            System.out.println("Map name unspecified, unsuccessful save.");
            return false;
        }
        try {
            FileOutputStream fileStream = new FileOutputStream(path());
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(this);
            objectStream.flush();
            objectStream.close();
            fileStream.close();
            System.out.println("File successfully saved.");
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("File unsuccessfully saved.");
            return false;
        }
    }

    public void load() {
        try {
            if (!UT_File.existingFile(path())) throw new FileNotFoundException("File doesn't exist");
            FileInputStream fileStream = new FileInputStream(path());
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            Map m = (Map) objectStream.readObject();
            w = m.w;
            h = m.h;
            tilemap = m.tilemap;
            tilemap.setParent(this);
            itemmap = m.itemmap;
            itemmap.setParent(this);
            for (Tile[] tileRow : tilemap.tiles()) {
                for (Tile t : tileRow) {
                    if (t != null) t.init();
                }
            }
            for (Item i : itemmap.ITEMS) {
                i.init();
            }
            fileStream.close();
            objectStream.close();
            System.out.println("File successfully loaded.");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("File unable to be loaded, default map loaded.");
        }
    }

    public void reset() {
        load();
        setMapAdjustX(0);
        setMapAdjustY(0);
    }

    public void erase() {
        tilemap = new TileMap(this);
        itemmap = new ItemMap(this);
        setMapAdjustX(0);
        setMapAdjustY(0);
    }

    public void rename(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Bad name, unsuccessful rename.");
        }
        else if (Objects.equals(this.name, name)) {
            System.out.println("No rename needed.");
        }
        else {
            if (UT_File.existingFile(path(name)))
                System.out.println("File already exists.");
            UT_File.renameFile(path(), path(name));
            this.name = name;
        }
    }

    public void delete() {
        UT_File.deleteFile(path());
    }

    @Override
    public void draw(Graphics2D G) {
        tilemap.draw(G);
        itemmap.draw(G);
        if (grid) UT_Draw.drawGrid(x + mapAdjustX, x + mapAdjustX + w, y + mapAdjustY, y + mapAdjustY + h, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, Color.WHITE, G);
    }

    String path() {
        return path(name);
    }

    String path(String n) {
        return "src\\Resources\\Saved_Maps\\" + n + ".ser";
    }
}
