package Interface.Components.Text;

import Markers.Area;
import Markers.Drawing;
import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public abstract class Text extends Area implements Drawing {
    //Used to get height/width of fonts, not the best method, but it works
    static final BufferedImage IMG = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    protected Color color;
    protected Font font;
    protected String text;

    public Text() {
        color = Color.BLACK;
        font = UT_Draw.FONT;
        text = "";
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public void setFont(Font f) {
        font = f;
        w = textWidth();
        h = textHeight();
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setText(String s) {
        text = s;
        w = textWidth();
    }

    public int textHeight() {
        return IMG.getGraphics().getFontMetrics(font).getHeight();
    }

    public int textWidth() {
        return IMG.getGraphics().getFontMetrics(font).stringWidth(text);
    }

    public int textWidth(String s) {
        return IMG.getGraphics().getFontMetrics(font).stringWidth(s);
    }
}
