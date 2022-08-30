package Interface.Page;


import Central.Main;
import Interface.Components.Images.Image;
import Interface.Components.Text.LineText;
import Utilities.UT_Draw;
import Utilities.UT_HTML;

import java.awt.Color;
import java.awt.Graphics2D;

public class Start extends Page {
    final LineText THE;
    final LineText GAME;
    final LineText SUBTITLE;
    final LineText ONWARD;
    final Image ONWARD_BG;

    public Start() {
        THE = new LineText("THE", FONT_L, PURE_WHITE);
        GAME = new LineText("GAME", FONT_L, PURE_WHITE);
        SUBTITLE = new LineText("A Tile-Editor and a 2D-Platform Game", FONT_XS, BLUE);
        ONWARD = new LineText("ONWARD", FONT_M, PURE_WHITE);
        ONWARD_BG = new Image(Main.G_IMAGE_MANAGER.BUTTON_BG);
        ONWARD_BG.setW(200);
        ONWARD_BG.setH(96);
    }

    @Override
    public void open() {
        frameDimensions(700,450);
        screenResize();
    }

    @Override
    public void screenResize() {
        NAVIGATION.screenResize();
        COPYRIGHT.screenResize();
        final int SCREEN_WIDTH = Main.WINDOW.SCREEN.screenWidth();
        final int SPACING = 20;
        UT_HTML.centerAreaX(THE, 0, SCREEN_WIDTH);
        UT_HTML.centerAreaX(GAME, 0, SCREEN_WIDTH);
        UT_HTML.centerAreaX(SUBTITLE, 0, SCREEN_WIDTH);
        THE.setY(80);
        UT_HTML.positioning(THE, SUBTITLE, UT_HTML.MAIN_AXIS.ABOVE, UT_HTML.CROSS_AXIS.MIDDLE, SPACING);
        UT_HTML.positioning(THE, GAME, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.MIDDLE, SPACING);
        UT_HTML.positioning(GAME, ONWARD_BG, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.MIDDLE, SPACING);
        UT_HTML.centerAreaX(ONWARD_BG, ONWARD);
        UT_HTML.centerAreaY(ONWARD_BG, ONWARD);
        ONWARD.addX(3);
    }

    @Override
    public void mouseClick() {
        if (ONWARD_BG.contains(Main.WINDOW.MOUSE.clickX(), Main.WINDOW.MOUSE.clickY())) {
            Main.INTERFACE.go(Main.INTERFACE.VIEW);
        }
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.fill(Color.BLACK, G);
        COPYRIGHT.draw(G);
        THE.drawShadow(G, OFFWHITE, 0, 4);
        THE.draw(G);
        GAME.drawShadow(G, OFFWHITE, 0, 4);
        GAME.draw(G);
        SUBTITLE.drawShadow(G, DARK_BLUE, 0, 1);
        SUBTITLE.draw(G);
        ONWARD_BG.draw(G);
        ONWARD.drawShadow(G, LIGHT_GRAY, 0, 3);
        ONWARD.draw(G);
    }
}
