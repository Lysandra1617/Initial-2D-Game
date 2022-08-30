package Maps.Units.Item;

import Markers.Drawing;

import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;

public class Vertices implements Drawing, Serializable {
    @Serial
    private static final long serialVersionUID = 21234L;
    final Item PARENT;
    final Vertex TOP_LEFT = new Vertex(this);
    final Vertex TOP_RIGHT = new Vertex(this);
    final Vertex BOTTOM_LEFT = new Vertex(this);
    final Vertex BOTTOM_RIGHT = new Vertex(this);
    int translationOriginX = -1;
    int translationOriginY = -1;
    Vertex manipulation;

    Vertices(Item item) {
        PARENT = item;
        init();
    }

    public void init() {
        TOP_LEFT.setX(PARENT.getX());
        TOP_LEFT.setY(PARENT.getY());
        TOP_RIGHT.setX(PARENT.getX() + PARENT.getW());
        TOP_RIGHT.setY(PARENT.getY());
        BOTTOM_LEFT.setX(PARENT.getX());
        BOTTOM_LEFT.setY(PARENT.getY() + PARENT.getH());
        BOTTOM_RIGHT.setX(PARENT.getX() + PARENT.getW());
        BOTTOM_RIGHT.setY(PARENT.getY() + PARENT.getH());
    }

    public void update(Vertex v) {
        if (v == TOP_LEFT) {
            TOP_LEFT.setX(PARENT.getX());
            TOP_LEFT.setY(PARENT.getY());
        }
        else if (v == TOP_RIGHT) {
            TOP_RIGHT.setX(PARENT.getX() + PARENT.getW());
            TOP_RIGHT.setY(PARENT.getY());
        }
        else if (v == BOTTOM_LEFT) {
            BOTTOM_LEFT.setX(PARENT.getX());
            BOTTOM_LEFT.setY(PARENT.getY() + PARENT.getH());
        }
        else if (v == BOTTOM_RIGHT) {
            BOTTOM_RIGHT.setX(PARENT.getX() + PARENT.getW());
            BOTTOM_RIGHT.setY(PARENT.getY() + PARENT.getH());
        }
    }

    public Vertex find(int pointX, int pointY) {
        if (TOP_LEFT.contains(pointX, pointY)) {
            manipulation = TOP_LEFT;
            return TOP_LEFT;
        }
        if (TOP_RIGHT.contains(pointX, pointY)) {
            manipulation = TOP_RIGHT;
            return TOP_RIGHT;
        }
        if (BOTTOM_LEFT.contains(pointX, pointY)) {
            manipulation = BOTTOM_LEFT;
            return BOTTOM_LEFT;
        }
        if (BOTTOM_RIGHT.contains(pointX, pointY)) {
            manipulation = BOTTOM_RIGHT;
            return BOTTOM_RIGHT;
        }
        return null;
    }

    public void manipulate(int pointX, int pointY) {
        int cvX;
        int cvY;
        int dX = pointX - manipulation.getX();
        int dY = pointY - manipulation.getY();

        if (manipulation == TOP_LEFT) {
            cvX = BOTTOM_RIGHT.getX();
            cvY = BOTTOM_RIGHT.getY();
            if (pointY < cvY && pointX < cvX) {
                PARENT.addX(dX);
                PARENT.addY(dY);
                PARENT.addW(-dX);
                PARENT.addH(-dY);
                update(TOP_LEFT);
                update(TOP_RIGHT);
                update(BOTTOM_LEFT);
            }
        }
        else if (manipulation == TOP_RIGHT) {
            cvX = BOTTOM_LEFT.getX();
            cvY = BOTTOM_LEFT.getY();
            if (pointY < cvY && pointX > cvX) {
                PARENT.setX(cvX);
                PARENT.addY(dY);
                PARENT.addW(dX);
                PARENT.addH(-dY);
                update(TOP_LEFT);
                update(TOP_RIGHT);
                update(BOTTOM_RIGHT);
            }
        }
        else if (manipulation == BOTTOM_LEFT) {
            cvX = TOP_RIGHT.getX();
            cvY = TOP_RIGHT.getY();
            if (pointY > cvY && pointX < cvX) {
                PARENT.addX(dX);
                PARENT.addW(-dX);
                PARENT.addH(dY);
                update(TOP_LEFT);
                update(BOTTOM_LEFT);
                update(BOTTOM_RIGHT);
            }

        }
        else if (manipulation == BOTTOM_RIGHT) {
            cvX = TOP_LEFT.getX();
            cvY = TOP_LEFT.getY();
            if (pointY > cvY && pointX > cvX) {
                PARENT.addW(dX);
                PARENT.addH(dY);
                update(TOP_RIGHT);
                update(BOTTOM_LEFT);
                update(BOTTOM_RIGHT);
            }
        }
    }

    public void setTranslationOrigin(int pointX, int pointY) {
        translationOriginX = pointX;
        translationOriginY = pointY;
    }

    public void translation(int pointX, int pointY) {
        int relX = translationOriginX - TOP_LEFT.getX();
        int relY = translationOriginY - TOP_LEFT.getY();
        PARENT.setX(pointX - relX);
        PARENT.setY(pointY - relY);
        setTranslationOrigin(pointX, pointY);
        init();
    }

    @Override
    public void draw(Graphics2D G) {
        TOP_LEFT.draw(G);
        TOP_RIGHT.draw(G);
        BOTTOM_LEFT.draw(G);
        BOTTOM_RIGHT.draw(G);
    }
}
