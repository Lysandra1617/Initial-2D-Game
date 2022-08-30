package Interface.Components.Text;

import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class LineText extends Text {

    public LineText(String s, Font f, Color c) {
        setColor(c);
        setFont(f);
        setText(s);
    }

    @Override
    public void draw(Graphics2D g) {
        UT_Draw.drawString(x, y + h, text, font, color, g);
    }

    public void draw(Graphics2D g, Color c) {
        UT_Draw.drawString(x, y + h, text, font, c, g);
    }

    public void drawShadow(Graphics2D g, Color c, int offX, int offY) {
        UT_Draw.drawString(x + offX, y + h + offY, text, font, c, g);
    }
}
