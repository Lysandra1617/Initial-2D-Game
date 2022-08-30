package Interface.Components.Layout;

import Central.Main;
import Interface.Components.Buttons.ImageButton;
import Interface.Components.Images.PatchBG;
import Markers.Area;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_Draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Gallery extends Area implements Respond, Drawing {
    private static class Slot extends Area implements Drawing {
        final int ID; //When a slot is picked, its ID is reported
        boolean highlight;
        BufferedImage image;
        protected int slide; //The slide of the slot
        protected int row; //The row of the slot
        protected int col; //The column of the slot

        Slot(int id, BufferedImage img) {
            ID = id;
            image = img;
            highlight = false;
        }

        void note(int s, int r, int c) {
            slide = s;
            row = r;
            col = c;
        }

        @Override
        public void draw(Graphics2D G) {
            UT_Draw.drawImage(x,y,w,h,image,G);
        }
    }

    //SLOT
    Slot selectedSlot;
    int slideCount; //# of slides
    int slotRows; //# of rows in each slide
    int slotColumns; //# of columns in each slide
    Slot[][][] slots; //[slide #][row][col]
    int slide;

    //LAYOUT
    final int SLOT_WIDTH = 50;
    final int SLOT_HEIGHT = 50;
    final int SLOT_SPACE = 4;
    final int NAVIGATION_BTN_W = 32;
    final int NAVIGATION_BTN_H = 32;
    final int NAVIGATION_BTN_GAP = 2;
    final int EXIT_BTN_W = 24;
    final int EXIT_BTN_H = 24;
    final int VERTICAL_STRETCH = 40;
    final int HORIZONTAL_STRETCH = 40;

    //LAYOUT PT.2
    Group container;
    Group[] slotSlides; //The slots of a particular slide are bunched together in a group, all those slides are here
    Group navigationButtons;
    ImageButton left;
    ImageButton right;
    ImageButton exit;

    //LAYOUT PT.3
    final PatchBG BG;

    public Gallery(int rows, int columns, BufferedImage[] images) {
        slideCount = (int) Math.ceil(1.0 * images.length / (rows * columns));
        slotRows = rows;
        slotColumns = columns;
        slots = new Slot[slideCount][slotRows][slotColumns];
        loadImages(images);
        slotSlides = new Group[slideCount];
        int slideWidth = columns * SLOT_WIDTH + Math.max(columns - 1, 0) * SLOT_SPACE;
        int slideHeight = rows * SLOT_HEIGHT + Math.max(rows - 1, 0) * SLOT_SPACE;
        for (int s = 0; s < slideCount; s++) {
            slotSlides[s] = new Group();
            slotSlides[s].setW(slideWidth);
            slotSlides[s].setH(slideHeight);
            fillSlide(s);
        }

        left = new ImageButton();
        left.setW(NAVIGATION_BTN_W);
        left.setH(NAVIGATION_BTN_H);
        left.setImage(Main.G_IMAGE_MANAGER.SHORT_L);
        right = new ImageButton();
        right.setW(NAVIGATION_BTN_W);
        right.setH(NAVIGATION_BTN_H);
        right.setImage(Main.G_IMAGE_MANAGER.SHORT_R);
        exit = new ImageButton();
        exit.setW(EXIT_BTN_W);
        exit.setH(EXIT_BTN_H);
        exit.setImage(Main.G_IMAGE_MANAGER.LETTER_X);

        navigationButtons = new Group();
        navigationButtons.setW(NAVIGATION_BTN_W * 2 + NAVIGATION_BTN_GAP);
        navigationButtons.setH(NAVIGATION_BTN_H);
        navigationButtons.add(left, 0, 0);
        navigationButtons.add(right, -1, 0);

        container = new Group();
        container.setW(Math.max(navigationButtons.getW(), slideWidth) + EXIT_BTN_W + HORIZONTAL_STRETCH);
        container.setH(navigationButtons.getH() + slideHeight + EXIT_BTN_H + VERTICAL_STRETCH);

        container.addC(slotSlides[slide]);
        container.addCX(navigationButtons,0,-10);
        container.add(exit, -10, 10);

        w = container.getW();
        h = container.getH();

        BG = new PatchBG(this);
        w = BG.getW();
        h = BG.getH();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        container.setX(x);
        BG.setX(x);
        for (int s = 0; s < slideCount; s++) {
            //Used so the x-position of the slots are maintained
            container.preposition(slotSlides[s], true, true, null, null, null, null);
        }
    }

    @Override
    public void setY(int y) {
        this.y = y;
        container.setY(y);
        BG.setY(y);
        for (int s = 0; s < slideCount; s++) {
            //Used so the y-position of the slots are maintained
            container.preposition(slotSlides[s], true, true, null, null, null, null);
        }
    }

    @Override
    public void mouseClick() {
        container.mouseClick();
        if (right.on()) {
            slide++;
            if (slide >= slideCount) slide = 0;
            container.replace(0, slotSlides[slide]);
        }
        else if (left.on()) {
            slide--;
            if (slide < 0) slide = slideCount - 1;
            container.replace(0, slotSlides[slide]);
        }

        int mouseX = Main.WINDOW.MOUSE.clickX();
        int mouseY = Main.WINDOW.MOUSE.clickY();

        if (slotSlides[slide].contains(mouseX, mouseY)) {
            int slotRow = (mouseY - slotSlides[slide].getY()) / SLOT_HEIGHT;
            int slotCol = (mouseX - slotSlides[slide].getX()) / SLOT_WIDTH;
            boolean badSlot = slotRow >= slots[0].length || slotCol >= slots[0][0].length || slots[slide][slotRow][slotCol] == null;
            unpickSlot();
            if (!badSlot || slots[slide][slotRow][slotCol].contains(mouseX, mouseY)) {
                pickSlot(slide, slotRow, slotCol);
            }
        }
    }

    @Override
    public void draw(Graphics2D G) {
        BG.draw(G);
        container.draw(G);
        highlightSlot(G);
    }

    public ImageButton exit() {
        return exit;
    }

    public int pickedID() {
        if (selectedSlot == null) return -1;
        return selectedSlot.ID;
    }

    void loadImages(BufferedImage[] images) {
        int i = 0;
        int imgLength = images.length;
        for (int s = 0; s < slideCount; s++) {
            for (int r = 0; r < slotRows; r++) {
                for (int c = 0; c < slotColumns; c++) {
                    if (i < imgLength) {
                        slots[s][r][c] = new Slot(i, images[i]);
                        slots[s][r][c].setW(SLOT_WIDTH);
                        slots[s][r][c].setH(SLOT_HEIGHT);
                        i++;
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }

    void fillSlide(int slide) {
        for (int r = 0; r < slotRows; r++) {
            for (int c = 0; c < slotColumns; c++) {
                if (slots[slide][r][c] != null) {
                    //We're adding slots to the corresponding group
                    slots[slide][r][c].note(slide, r, c);
                    int rxPos = c * (SLOT_WIDTH + SLOT_SPACE);
                    int ryPos = r * (SLOT_HEIGHT + SLOT_SPACE);
                    slotSlides[slide].add(slots[slide][r][c], rxPos, ryPos);
                }
            }
        }
    }

    void pickSlot(int s, int r, int c) {
        selectedSlot = slots[s][r][c];
        selectedSlot.highlight = true;
    }

    void unpickSlot() {
        if (selectedSlot == null) return;
        selectedSlot.highlight =false;
        selectedSlot = null;
    }

    void highlightSlot(Graphics2D G) {
        if (selectedSlot == null || slide != selectedSlot.slide) return;
        if (selectedSlot.highlight) {
            UT_Draw.drawRectangle(selectedSlot.getX(), selectedSlot.getY(), selectedSlot.getW(), selectedSlot.getH(), false, Color.YELLOW, G);
        }
    }
}
