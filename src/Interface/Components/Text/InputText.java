package Interface.Components.Text;

import Central.Main;
import Markers.Respond;
import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class InputText extends Text implements Respond {
    final int MAX_INPUT_WIDTH; //The input's width cannot exceed this
    StringBuilder input; //All the text the user has entered. What appears on screen is stored in the parent text variable
    int inputWidth; //The width of the current input
    boolean numFlag; //Only numbers allowed
    boolean fileFlag; //Only numbers and letters allowed
    boolean active; //If the input is active, the user is going to type

    //Blinking Cursor
    boolean blink;
    int blinkThreshold;
    int blinkCount;

    public InputText(String def, Font f, Color c, int width) {
        setFont(f);
        setColor(c);
        MAX_INPUT_WIDTH = width;
        w = width;
        h += 10;
        text = "";
        active = false;
        numFlag = false;
        setInput(def); //def is the default string for the input
        blink = true;
        blinkThreshold = 25;
        blinkCount = 0;
    }

    public void setNumFlag(boolean b) {
        numFlag = b;
    }

    public void setFileFlag(boolean b) {
        fileFlag = b;
    }

    public void setInput(String s) {
        inputWidth = 0;
        input = new StringBuilder();
        text = "";
        for (char c : s.toCharArray()) {
            manualKeyType(c, KeyEvent.getExtendedKeyCodeForChar(c));
        }
    }

    public String getInput() {
        return String.valueOf(input);
    }

    public boolean active() {
        return active;
    }

    @Override
    public void mouseClick() {
        active = contains(Main.WINDOW.MOUSE.clickX(), Main.WINDOW.MOUSE.clickY());
    }

    @Override
    public void keyType() {
        if (!active) return;
        manualKeyType(Main.WINDOW.KEY.key(), Main.WINDOW.KEY.keyCode());
    }

    @Override
    public void draw(Graphics2D G) {
        UT_Draw.drawRectangle(x, y, w, h, false, Color.BLACK, G);
        if (active) {
            blink(G);
        }
        if (!text.isEmpty()) {
            int textSpacing = 5;
            UT_Draw.drawString(x + textSpacing, y + h - textSpacing, text, font, Color.BLACK, G);
        }
    }

    void blink(Graphics2D G) {
        if (blink) {
            int dist = Math.min(x + inputWidth + 5, x + MAX_INPUT_WIDTH - 5) + 2; //Cursor's x-position
            UT_Draw.drawVLine(dist, y + 3,y + h - 3, Color.BLACK, G); //Drawing the cursor
        }
        blinkCount++;
        if (blinkCount >= blinkThreshold) {
            blink = !blink;
            blinkCount = 0;
        }
    }

    void manualKeyType(char key, int keyCode) {
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                inputWidth = textWidth(String.valueOf(input));

                if (inputWidth < MAX_INPUT_WIDTH)
                    text = String.valueOf(input);
            }
        }
        else {
            if (check(key)) { //Checking the entered key to see if it passes the flags, if any
                input.append(key);
            }
            if (textWidth(String.valueOf(input)) < MAX_INPUT_WIDTH) { //Only adding the text if there's space
                inputWidth = textWidth(String.valueOf(input));
                text = String.valueOf(input);
            }
        }
    }

    boolean check(char key) {
        if (numFlag)
            return Character.isDigit(key);
        else if (fileFlag)
            return Character.isDigit(key) || Character.isLetter(key);
        else
            return true;
    }
}
