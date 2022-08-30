package Maps.Game.Entity.NPC;

import Maps.Game.Entity.Entity;
import Maps.Game.GameMap;
import Maps.Units.Tile;
import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Graphics2D;

public class NPC extends Entity {
    PathFinder path;
    Entity target;

    public NPC(int w, int h) {
        super(w, h);
    }

    @Override
    public void setMap(GameMap gm) {
        super.setMap(gm);
        path = new PathFinder(gm.tilemap().tiles());
    }

    public void setTarget(Entity e) {
        target = e;
    }

    int targetRow, targetCol;

    public void follow() {
        if (path == null) return;
        int currRow = (this.y + this.h / 2) / Tile.TILE_HEIGHT;
        int currCol = (this.x + this.w / 2) / Tile.TILE_WIDTH;
        targetRow = (target.getY() + target.getH() / 2) / Tile.TILE_HEIGHT;
        targetCol = (target.getX() + target.getW() / 2) / Tile.TILE_WIDTH;
        path.setBegin(targetRow, targetCol);
        path.setEnd(currRow, currCol);
        path.solve();
        if (path.path().size() >= 2) {
            boolean moveLeft, moveRight, moveUp;
            int nextRow = path.path().get(0).row;
            int nextCol = path.path().get(0).col;
            if (currRow == nextRow) nextRow = path.path().get(1).row;
            if (currCol == nextCol) nextCol = path.path().get(1).col;
            moveUp = (currRow > nextRow);
            moveRight = (currCol < nextCol);
            moveLeft = (currCol > nextCol);

            if (moveUp) moveJump();
            if (moveLeft) moveLeft();
            if (moveRight) moveRight();
        }
    }

    @Override
    public void update() {
        follow();
        pseudoJump();
        pseudoGravity();
        x += directionX;
        y += directionY;
        y += gravity;
        if (jumping) y -= jump;
        if (aabb != null) aabb.handle();
        directionX = 0;
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawRectangle(x,y,w,h,true, Color.RED,G);
    }
}
