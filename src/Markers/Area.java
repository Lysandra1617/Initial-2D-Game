package Markers;

import java.io.Serial;
import java.io.Serializable;

public class Area implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    protected int x;
    protected int y;
    protected int w;
    protected int h;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void addX(int _x) {
        setX(x + _x);
    }

    public void addY(int _y) {
        setY(y + _y);
    }

    public void addW(int _w) {
        setW(w + _w);
    }

    public void addH(int _h) {
        setH(h + _h);
    }

    public boolean contains(int x1, int y1) {
        return x1 >= x && x1 <= x + w && y1 >= y && y1 <= y + h;
    }

    public int left() {
        return x;
    }

    public int top() {
        return y;
    }

    public int right() {
        return x + w;
    }

    public int bottom() {
        return y + h;
    }

}
