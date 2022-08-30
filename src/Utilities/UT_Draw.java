package Utilities;

import Central.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UT_Draw {
    public static final int FONT_SIZE = 12;
    public static final Font FONT = new Font(Font.DIALOG, Font.BOLD, FONT_SIZE);
    public static Font pixelFont;

    public static void init() {
        pixelFont = UT_File.getFont("src\\Resources\\GUI\\Font\\PIXEL.ttf");
        if (pixelFont == null) pixelFont = FONT;
    }

    public static void drawLine(int x1, int y1, int x2, int y2, Color c, Graphics2D g) {
        if (c != null) g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
        if (c != null) reset(g);
    }

    public static void drawVLine(int x, int y1, int y2, Color c, Graphics2D g) {
        drawLine(x, y1, x, y2, c, g);
    }

    public static void drawHLine(int x1, int x2, int y, Color c, Graphics2D g) {
        drawLine(x1, y, x2, y, c, g);
    }

    public static void drawRectangle(int x, int y, int w, int h, boolean fill, Color c, Graphics2D g) {
        if (c != null) g.setColor(c);
        if (fill)
            g.fillRect(x, y, w, h);
        else
            g.drawRect(x, y, w, h);
        if (c != null) reset(g);
    }

    public static void drawImage(int x, int y, int w, int h, BufferedImage img, Graphics2D g) {
        g.drawImage(img, x, y, w, h, null);
    }

    public static void drawString(int x, int y, String s, Font f, Color c, Graphics2D g) {
        if (f != null) g.setFont(f);
        if (c != null) g.setColor(c);
        g.drawString(s, x, y);
        if (f != null || c != null)
            reset(g);
    }

    public static void drawGrid(int left, int right, int top, int bottom, int unitWidth, int unitHeight, Color color, Graphics2D g) {
        if (color != null)
            g.setColor(color);
        for (int i = left; i <= right; i += unitWidth)
            drawLine(i, top, i, bottom, color, g);
        for (int j = top; j <= bottom; j += unitHeight)
            drawLine(left, j, right, j, color, g);
        if (color != null)
            reset(g);
    }

    public static void fill(Color c, Graphics2D g) {
        drawRectangle(0, 0, Main.WINDOW.SCREEN.screenWidth(), Main.WINDOW.SCREEN.screenHeight(),true, c, g);
    }

    static void reset(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(FONT);
    }
}
