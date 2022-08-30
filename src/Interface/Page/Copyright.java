package Interface.Page;

import Central.Main;
import Interface.Components.Text.LineText;
import Markers.Area;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_HTML;

import java.awt.Color;
import java.awt.Graphics2D;

public class Copyright extends Area implements Respond, Drawing {
    LineText copy;

    public Copyright() {
        copy = new LineText("©Lysandra Belnavis-Walters", Page.FONT_S, Color.WHITE);
        w = copy.getW();
        h = copy.getH();
    }

    @Override
    public void screenResize() {
        copy.setY(UT_HTML.positionFromY(0, Main.WINDOW.SCREEN.screenHeight(), copy.getH(), -1));
        UT_HTML.centerAreaX(copy, 0, Main.WINDOW.SCREEN.screenWidth());
        x = copy.getX();
        y = copy.getY();
    }

    @Override
    public void draw(Graphics2D G) {
        copy.draw(G);
    }
}
