package Interface.Page;

import Central.Main;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Font;

public abstract class Page implements Respond, Drawing {
    public static final Font FONT_XL = UT_Draw.pixelFont.deriveFont(75f);
    public static final Font FONT_L = UT_Draw.pixelFont.deriveFont(50f);
    public static final Font FONT_M = UT_Draw.pixelFont.deriveFont(30f);
    public static final Font FONT_MS = UT_Draw.pixelFont.deriveFont(20f);
    public static final Font FONT_S = UT_Draw.pixelFont.deriveFont(15f);
    public static final Font FONT_XS = UT_Draw.pixelFont.deriveFont(12f);
    public static final Font FONT_XXS = UT_Draw.pixelFont.deriveFont(10f);
    public static final Color CHARCOAL = new Color(10, 10, 10);
    public static final Color PURE_WHITE = new Color(255, 255, 255);
    public static final Color OFFWHITE = new Color(168, 164, 164);
    public static final Color LIGHT_GRAY = new Color(84, 82, 82);
    public static final Color GRAY = new Color(59, 57, 57);
    public static final Color DARK_GRAY = new Color(38, 37, 37);
    public static final Color BLUE = new Color(22, 53, 180);
    public static final Color DARK_BLUE = new Color(23, 25, 150);
    public static final Navigation NAVIGATION = new Navigation();
    public static final Copyright COPYRIGHT = new Copyright();

    public static void frameDimensions(int w, int h) {
        Main.WINDOW.frame().setSize(w, h);
        Main.WINDOW.frame().setLocationRelativeTo(null);
    }

    public void open() {}

    public void close() {}
}
