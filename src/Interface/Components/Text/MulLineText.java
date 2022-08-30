package Interface.Components.Text;

import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MulLineText extends Text {
    final ArrayList<String> LINES; //The text is broken down into lines and stored here
    int lineH; //Height of 1 line
    int lineSpace; //Spacing between each line

    public MulLineText(String s, Font f, Color c, int maxWidth, int lineSpacing) {
        LINES = new ArrayList<>();
        setText(s);
        setFont(f);
        setColor(c);
        lineSpace = lineSpacing;
        lineH = textHeight();
        w = maxWidth;
        wrap();
        updateDimensions();
    }

    @Override
    public void setText(String s) {
        text = s;
        if (w > 0) {
            wrap();
            updateDimensions();
        }
    }

    @Override
    public void draw(Graphics2D G) {
        int yPos = y + lineH;
        int xPos = x;
        for (String line : LINES) {
            UT_Draw.drawString(xPos, yPos, line, font, color, G);
            yPos += lineH + lineSpace;
        }
    }

    void wrap() {
        //This method is used to break down a line into lines. It's not the most precise, however.
        //I just look at the length of a single word, and if it fits, add to the current line, if not, create a new line and add it.
        final String[] WORDS = text.split(" ");
        final int[] WORD_LENGTHS = new int[WORDS.length];

        for (int i = 0; i < WORDS.length; i++) {
            WORD_LENGTHS[i] = textWidth(WORDS[i]);
        }

        StringBuilder string = new StringBuilder(WORDS[0]);
        int stringWidth = WORD_LENGTHS[0];

        for (int i = 1; i < WORDS.length; i++) {
            //The last word
            if (i == WORDS.length - 1) {
                if (w - stringWidth >= WORD_LENGTHS[i]) {
                    string.append(" ").append(WORDS[i]);
                    LINES.add(String.valueOf(string));
                }
                else {
                    LINES.add(String.valueOf(string));
                    LINES.add(WORDS[i]);
                }
            }
            //The word can fit into the current line
            else if (w - stringWidth >= WORD_LENGTHS[i]) {
                string.append(" ").append(WORDS[i]);
                stringWidth += WORD_LENGTHS[i];
            }
            //The word cannot fit into the current line
            else {
                LINES.add(String.valueOf(string));
                string.replace(0, string.length(), WORDS[i]);
                stringWidth = WORD_LENGTHS[i];
            }
        }
    }

    void updateDimensions() {
        h = LINES.size() * lineH + Math.max(0, LINES.size() - 1) * lineSpace;
        for (String s : LINES) w = Math.max(textWidth(s), w);
    }
}
