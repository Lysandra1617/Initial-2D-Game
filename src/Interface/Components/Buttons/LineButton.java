package Interface.Components.Buttons;

import Central.Main;
import Interface.Components.Images.Image;
import Interface.Components.Text.LineText;
import Utilities.UT_HTML;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static Utilities.UT_HTML.CROSS_AXIS.MIDDLE;

public class LineButton extends Button {
    final int GAP = 3;
    LineText label; //The text of the button
    Image image; //The image that appears when the button is being hovered
    final int IMAGE_W = 40;
    final int IMAGE_H = 40;
    boolean disableImage;

    public LineButton(String s, Font f, Color c) {
        label = new LineText(s, f, c);
        image = new Image(Main.G_IMAGE_MANAGER.SHORT_L);
        image.setW(IMAGE_W);
        image.setH(IMAGE_H);
        UT_HTML.positioning(label, image, UT_HTML.MAIN_AXIS.FRONT, MIDDLE, GAP);
        w = label.getW() + GAP + image.getW();
        h = Math.max(label.getH(), image.getH());
        disableImage = false;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        label.setX(x);
        image.setX(label.right() + GAP);
    }

    @Override
    public void setY(int y) {
        this.y = y;
        label.setY(y);
        UT_HTML.centerAreaY(label, image);
    }

    public String getText() {
        return label.getText();
    }

    public LineText getLabel() {
        return label;
    }

    public void overrideImageW(int w) {
        image.setW(w);
        this.w = label.getW() + GAP + image.getW();
    }

    public void overrideImageH(int h) {
        image.setH(h);
        this.h = Math.max(label.getH(), image.getH());
    }

    public void disableImage() {
        disableImage = true;
    }

    public void enableImage() {
        disableImage = false;
    }

    public int labelSize() {
        return label.getText().length();
    }

    @Override
    public void draw(Graphics2D G) {
        if (hover && !disableImage) {
            image.draw(G);
        }
        label.draw(G);
    }
}
