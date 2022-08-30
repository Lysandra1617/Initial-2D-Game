package Maps.Game;

import Central.Main;
import Maps.Game.Entity.Entity;
import Maps.Game.Entity.NPC.NPC;
import Maps.Map;
import Maps.Units.Item.Item;
import Maps.Units.Tile;
import Markers.Respond;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import static Maps.Units.MapUnit.inScope;
import static Utilities.UT_Draw.drawImage;
import static Utilities.UT_Draw.drawRectangle;

public class GameMap extends Map implements Respond {
    final Camera CAMERA;
    final Entity PLAYABLE_ENTITY;
    final NPC NPC;

    public GameMap(String mapName) {
        super(mapName);
        PLAYABLE_ENTITY = new Entity(48, 48);
        PLAYABLE_ENTITY.setMap(this);
        NPC = new NPC(48, 48);
        NPC.setMap(this);
        NPC.setTarget(PLAYABLE_ENTITY);
        CAMERA = new Camera();
        CAMERA.setFocus(PLAYABLE_ENTITY);
    }

    public GameMap() {
        PLAYABLE_ENTITY = new Entity(48, 48);
        NPC = new NPC(50, 50);
        NPC.setTarget(PLAYABLE_ENTITY);
        CAMERA = new Camera();
        CAMERA.setFocus(PLAYABLE_ENTITY);
    }

    @Override
    public void keyPress() {
        if (Main.WINDOW.KEY.pressed(Main.WINDOW.KEY.LEFT)) {
            PLAYABLE_ENTITY.moveLeft();
        }
        if (Main.WINDOW.KEY.pressed(Main.WINDOW.KEY.RIGHT)) {
            PLAYABLE_ENTITY.moveRight();
        }
        if (Main.WINDOW.KEY.pressed(Main.WINDOW.KEY.UP)) {
            PLAYABLE_ENTITY.moveUp();
        }
        if (Main.WINDOW.KEY.pressed(Main.WINDOW.KEY.DOWN)) {
            PLAYABLE_ENTITY.moveDown();
        }
        if (Main.WINDOW.KEY.pressed(Main.WINDOW.KEY.SPACE)) {
            PLAYABLE_ENTITY.moveJump();
        }
    }

    @Override
    public void keyRelease() {
        if (Main.WINDOW.KEY.released(Main.WINDOW.KEY.LEFT) || Main.WINDOW.KEY.released(Main.WINDOW.KEY.RIGHT)) {
            PLAYABLE_ENTITY.stopMovementX();
        }
        if (Main.WINDOW.KEY.released(Main.WINDOW.KEY.UP) || Main.WINDOW.KEY.released(Main.WINDOW.KEY.DOWN)) {
            PLAYABLE_ENTITY.stopMovementY();
        }
    }

    @Override
    public void screenResize() {
        CAMERA.screenResize();
    }

    @Override
    public void update() {
        PLAYABLE_ENTITY.update();
        //NPC.update();
        CAMERA.update();
    }

    @Override
    public void draw(Graphics2D G) {
        if (itemmap == null || itemmap.items() == null || tilemap == null || tilemap.tiles() == null) return;

        Tile[][] tiles = tilemap.tiles();

        int r1 = Math.max(0, CAMERA.drawnMap.drawRowStart);
        int r2 = Math.min(tiles.length - 1, CAMERA.drawnMap.drawRowEnd);

        int c1 = Math.max(0, CAMERA.drawnMap.drawColumnStart);
        int c2 = Math.min(tiles[0].length - 1, CAMERA.drawnMap.drawColumnEnd);

        int mapLeft = c1 * Tile.TILE_WIDTH;
        int mapRight = c2 * Tile.TILE_WIDTH;

        int mapTop = r1 * Tile.TILE_HEIGHT;
        int mapBottom = r2 * Tile.TILE_HEIGHT;

        ArrayList<Item> items = itemmap.items();

        //Particles in the Back
        for (int i = 0; i < itemmap.BASE_LAYER; i++) {
            if (i >= items.size()) break;
            Item p = items.get(i);
            if (inScope(p, mapLeft, mapRight, mapTop, mapBottom)) {
                drawImage(p.getX() + CAMERA.screenOffsetX, p.getY() + CAMERA.screenOffsetY, p.getW(), p.getH(), p.getImage(), G);
            }
        }

        //Tiles in the Middle
        for (int r = r1; r <= r2; r++) {
            for (int c = c1; c <= c2; c++) {
                Tile tile = tiles[r][c];
                if (tile != null) {
                    drawImage(tile.getX() + CAMERA.screenOffsetX, tile.getY() + CAMERA.screenOffsetY, tile.getW(), tile.getH(), tile.getImage(), G);
                }
            }
        }

        //Entity also in the Middle
        drawRectangle(NPC.getX() + CAMERA.screenOffsetX, NPC.getY() + CAMERA.screenOffsetY, NPC.getW(), NPC.getH(), true, Color.RED, G);
        drawRectangle(PLAYABLE_ENTITY.getX() + CAMERA.screenOffsetX, PLAYABLE_ENTITY.getY() + CAMERA.screenOffsetY, PLAYABLE_ENTITY.getW(), PLAYABLE_ENTITY.getH(), true, Color.BLUE, G);

        //Particles in the Front
        for (int i = itemmap.BASE_LAYER; i < items.size(); i++) {
            Item p = items.get(i);
            if (inScope(p, mapLeft, mapRight, mapTop, mapBottom)) {
                drawImage(p.getX() + CAMERA.screenOffsetX, p.getY() + CAMERA.screenOffsetY, p.getW(), p.getH(), p.getImage(), G);
            }
        }
    }

}
