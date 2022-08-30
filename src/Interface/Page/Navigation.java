package Interface.Page;

import Central.Main;
import Interface.Components.Buttons.ImageButton;
import Interface.Components.Text.LineText;
import Markers.Area;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_Draw;
import Utilities.UT_HTML;

import java.awt.Color;
import java.awt.Graphics2D;

public class Navigation extends Area implements Respond, Drawing {
    final ImageButton BACK;
    final LineText HOME;

    public Navigation() {
        h = 50;
        BACK = new ImageButton();
        BACK.setImage(Main.G_IMAGE_MANAGER.LONG_L);
        BACK.setW(52);
        BACK.setH(32);
        HOME = new LineText("HOME", Page.FONT_S, Color.WHITE);
    }

    @Override
    public void screenResize() {
        w = Main.WINDOW.SCREEN.screenWidth();
        BACK.setX(1);
        HOME.setX(UT_HTML.positionFromX(0, Main.WINDOW.SCREEN.screenWidth(), HOME.getW(),-10));
        UT_HTML.centerAreaY(this, BACK);
        UT_HTML.centerAreaY(this, HOME);
    }

    @Override
    public void mouseClick() {
        BACK.mouseClick();
        int clickX = Main.WINDOW.MOUSE.clickX();
        int clickY = Main.WINDOW.MOUSE.clickY();
        if (BACK.on()) Main.INTERFACE.back();
        else if (HOME.contains(clickX, clickY)) Main.INTERFACE.go(Main.INTERFACE.START);
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawRectangle(0, 0, w, h, true, Color.BLACK, G);
        BACK.draw(G);
        HOME.draw(G);
    }

}
