package Maps.Game.Entity;

import Maps.Units.Tile;
import Markers.Area;

public class AABB {
    Entity entity;
    Area[][] obstacles;
    int xPosPreCollision;
    int yPosPreCollision;

    public AABB(Entity e, Area[][] o) {
        entity = e;
        obstacles = o;
        xPosPreCollision = e.getX();
        yPosPreCollision = e.getY();
    }

    public void handle() {
        final int MAX_ROW = obstacles.length - 1;
        final int MAX_COL = obstacles[0].length - 1;
        final int ENTITY_ROW = entity.getY() / Tile.TILE_HEIGHT;
        final int ENTITY_COL = entity.getX() / Tile.TILE_WIDTH;
        final int ROW_SPAN = (int) Math.max(Math.max(Math.ceil((double)Math.abs(entity.directionY()) / Tile.TILE_HEIGHT), Math.abs(entity.gravity())), (entity.jumping()) ? Math.abs(entity.jump) : 0);
        final int COL_SPAN = (int) Math.ceil((double)Math.abs(entity.directionX()) / Tile.TILE_WIDTH);
        final int SPAN = Math.max(ROW_SPAN, COL_SPAN);
        final int ROW_0 = Math.max(0, ENTITY_ROW - SPAN);
        final int ROW_1 = Math.min(MAX_ROW, ENTITY_ROW + SPAN);
        final int COL_0 = Math.max(0, ENTITY_COL - SPAN);
        final int COL_1 = Math.min(MAX_COL, ENTITY_COL + SPAN);
        boolean noCollision = true;
        int entityW, entityH, entityLeft, entityRight, entityTop, entityBottom;
        int blockW, blockH, blockLeft, blockRight, blockTop, blockBottom;
        boolean collisionX, collisionY;

        for (int r = ROW_0; r <= ROW_1; r++) {
            for (int c = COL_0; c <= COL_1; c++) {
                if (obstacles[r][c] != null) {
                    entityW = entity.getW();
                    entityH = entity.getH();
                    entityLeft = entity.left();
                    entityRight = entity.right();
                    entityTop = entity.top();
                    entityBottom = entity.bottom();
                    blockW = obstacles[r][c].getW();
                    blockH = obstacles[r][c].getH();
                    blockLeft = obstacles[r][c].left();
                    blockRight = obstacles[r][c].right();
                    blockTop = obstacles[r][c].top();
                    blockBottom = obstacles[r][c].bottom();

                    if (aabbCollision(entityLeft, entityRight, entityTop, entityBottom, blockLeft, blockRight, blockTop, blockBottom)) {
                        noCollision = false;
                        collisionX = collisionX(entityLeft, entityRight, entityTop, entityBottom, blockLeft, blockRight, blockTop, blockBottom);
                        collisionY = collisionY(entityLeft, entityRight, entityTop, entityBottom, blockLeft, blockRight, blockTop, blockBottom);

                        if (collisionX && !collisionY) {
                            resolveX(entityLeft, entityW, blockLeft, blockW);
                            noCollision = true;
                        }
                        else if (!collisionX && collisionY) {
                            resolveY(entityTop, entityH, blockTop, blockH);
                            noCollision = true;
                        }
                        else {
                            if (wasOnTop(entityH, blockTop) || wasBelow(blockBottom)) {
                                resolveY(entityTop, entityH, blockTop, blockH);
                                entityTop = entity.top();
                                entityBottom = entity.bottom();
                                if (collisionX(entityLeft,entityRight,entityTop,entityBottom,blockLeft,blockRight,blockTop,blockBottom)) {
                                    resolveX(entityLeft, entityW, blockLeft, blockW);
                                }
                                noCollision = true;
                            }
                            else if (wasBehind(entityW, blockLeft) || wasInFront(blockRight)) {
                                resolveX(entityLeft, entityW, blockLeft, blockW);
                                entityLeft = entity.left();
                                entityRight = entity.right();
                                if (collisionY(entityLeft,entityRight,entityTop,entityBottom,blockLeft,blockRight,blockTop,blockBottom)) {
                                    resolveY(entityTop, entityH, blockTop, blockH);
                                }
                                noCollision = true;
                            }
                        }
                    }
                }
            }


        }

        if (noCollision) {
            xPosPreCollision = entity.getX();
            yPosPreCollision = entity.getY();
        }
    }

    boolean aabbCollision(int aLeft, int aRight, int aTop, int aBottom, int bLeft, int bRight, int bTop, int bBottom) {
        return aLeft < bRight && aRight > bLeft && aTop < bBottom && aBottom > bTop;
    }

    boolean collisionX(int aLeft, int aRight, int aTop, int aBottom, int bLeft, int bRight, int bTop, int bBottom) {
        return aTop > bTop && aBottom < bBottom && ((aLeft > bLeft && aLeft < bRight) || (aRight > bLeft && aRight < bRight));
    }

    boolean collisionY(int aLeft, int aRight, int aTop, int aBottom, int bLeft, int bRight, int bTop, int bBottom) {
        return aLeft > bLeft && aRight < bRight && ((aBottom > bTop && aBottom < bBottom) || (aTop < bBottom && aTop > bTop));
    }

    boolean wasOnTop(int aHeight, int bTop) {
        int aBottom = yPosPreCollision + aHeight;
        return aBottom <= bTop;
    }

    boolean wasBelow(int bBottom) {
        int aTop = yPosPreCollision;
        return aTop >= bBottom;
    }

    boolean wasBehind(int aWidth, int bLeft) {
        int aRight = xPosPreCollision + aWidth;
        return aRight <= bLeft;
    }

    boolean wasInFront(int bRight) {
        int aLeft = xPosPreCollision;
        return aLeft >= bRight;
    }

    void resolveY(int aTop, int aHeight, int bTop, int bHeight) {
        int aimY;
        int change;

        if (entity.directionY() >= 0 && !entity.jumping()) {
            aimY = bTop - aHeight;
            entity.resetGravity();
        }
        else if (entity.directionY() < 0 || entity.jumping()) {
            aimY = bTop + bHeight;
            entity.resetJump();
        }
        else {
            return;
        }
        change = aimY - aTop;
        entity.moveY(change);
    }

    void resolveX(int aLeft, int aWidth, int bLeft, int bWidth) {
        int aimX;
        int change;

        if (entity.directionX() > 0) {
            aimX = bLeft - aWidth;
        }
        else if (entity.directionX() < 0) {
            aimX = bLeft + bWidth;
        }
        else {
            return;
        }
        change = aimX - aLeft;
        entity.moveX(change);
    }
}
