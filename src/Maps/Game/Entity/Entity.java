package Maps.Game.Entity;

import Maps.Game.GameMap;
import Markers.Area;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity extends Area implements Respond, Drawing {
    protected int directionX, directionY;
    int stepDistance;
    protected GameMap map;
    protected AABB aabb;
    int g = 5;
    int gTime = 0;
    protected int gravity = 0;
    protected boolean jumping;
    final int JUMP_VEL_0 = 20;
    protected int jump = JUMP_VEL_0;

    public Entity(int w, int h) {
        stepDistance = 10;
        this.w = w;
        this.h = h;
    }

    public void setMap(GameMap gm) {
        map = gm;
        aabb = new AABB(this, map.tilemap().tiles());
    }

    public int directionX() {
        return directionX;
    }

    public int directionY() {
        return directionY;
    }

    public int gravity() {
        return gravity;
    }

    public int jump() {
        return jump;
    }

    public boolean jumping() {
        return jumping;
    }

    public void moveLeft() {
        directionX = -stepDistance;
    }

    public void moveRight() {
        directionX = stepDistance;
    }

    public void moveUp() {
        directionY = -stepDistance;
    }

    public void moveDown() {
        directionY = stepDistance;
    }

    public void moveJump() {
        if (!jumping) jumping = true;
    }

    public void pseudoJump() {
        if (jumping) {
            jump = (int) (jump * 0.90);
            if (jump <= 0) {
                jumping = false;
            }
        }
        else {
            jump = JUMP_VEL_0;
        }
    }

    public void resetJump() {
        jumping = false;
        jump = JUMP_VEL_0;
    }

    public void setDirectionX(int x) {
        directionX = x;
    }

    public void setDirectionY(int y) {
        directionY = y;
    }

    public void moveX(int _x) {
        addX(_x);
    }

    public void moveY(int _y) {
        addY(_y);
    }

    public void stopMovementX() {
        directionX = 0;
    }

    public void stopMovementY() {
        directionY = 0;
    }

    public void pseudoGravity() {
        if (jumping) return;
        if (directionY == 0) {
            gravity = gTime * g;
            gTime++;
        }
        else {
            gTime = 0;
        }
    }

    public void resetGravity() {
        gTime = 0;
        directionY = 0;
        gravity = 0;
    }

    @Override
    public void update() {
        pseudoJump();
        pseudoGravity();
        x += directionX;
        y += directionY;
        y += gravity;
        if (jumping) y -= jump;
        if (aabb != null) aabb.handle();
   }

   @Override
   public void draw(Graphics2D G) {
       UT_Draw.drawRectangle(x,y,w,h,true, Color.BLUE,G);
   }
}
