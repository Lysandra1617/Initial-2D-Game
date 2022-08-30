package Interface.Page.Editor;

import Central.Main;
import Interface.Components.Buttons.ImageButton;
import Interface.Components.Buttons.ToggleButton;
import Interface.Components.Layout.Gallery;
import Interface.Components.Text.InputText;
import Interface.Components.Text.LineText;
import Maps.Map;
import Maps.Units.Item.Item;
import Markers.Drawing;
import Utilities.UT_HTML;

import static Interface.Page.Editor.Editor.popToggles;
import static java.awt.Color.BLACK;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;

public class ItemAction extends Action implements Drawing {
    final LineText TITLE;
    final LineText ADD_LINE;
    final ToggleButton ADD_TOGGLE;
    final ImageButton GALLERY_BUTTON;
    final Gallery GALLERY;
    final LineText SELECT_LINE;
    final ToggleButton SELECT_TOGGLE;
    Item pickedItem;
    Item movingItem;
    Item sizingItem;
    final LineText ITEM_STAT_LINE;
    final int INPUT_WIDTH = 40;
    final LineText ITEM_ID_LINE;
    final LineText ITEM_X_LINE;
    final LineText ITEM_Y_LINE;
    final LineText ITEM_W_LINE;
    final LineText ITEM_H_LINE;
    final InputText ITEM_ID;
    final InputText ITEM_X;
    final InputText ITEM_Y;
    final InputText ITEM_W;
    final InputText ITEM_H;

    ItemAction(Map m, Editor main) {
        super(m, main);
        TITLE = new LineText("ITEMS", FONT_S, BLACK);
        ADD_LINE = new LineText("Add", FONT_XS, BLACK);
        ADD_TOGGLE = new ToggleButton();
        GALLERY_BUTTON = new ImageButton();
        GALLERY_BUTTON.setImage(Main.G_IMAGE_MANAGER.TRANSP_BOX);
        GALLERY_BUTTON.setW(20);
        GALLERY_BUTTON.setH(20);
        GALLERY = new Gallery(3,3,Main.MAP_IMAGE_MANAGER.ITEM_IMAGES);
        SELECT_LINE = new LineText("Select", FONT_XS, BLACK);
        SELECT_TOGGLE = new ToggleButton();
        ITEM_STAT_LINE = new LineText("INFO", FONT_XS, BLACK);
        ITEM_ID_LINE = new LineText("id:", FONT_XXS, BLACK);
        ITEM_X_LINE = new LineText("x:", FONT_XXS, BLACK);
        ITEM_Y_LINE = new LineText("y:", FONT_XXS, BLACK);
        ITEM_W_LINE = new LineText("w:", FONT_XXS, BLACK);
        ITEM_H_LINE = new LineText("h:", FONT_XXS, BLACK);
        ITEM_ID = new InputText("", FONT_XXS, BLACK, INPUT_WIDTH);
        ITEM_X = new InputText("", FONT_XXS, BLACK, INPUT_WIDTH);
        ITEM_Y = new InputText("", FONT_XXS, BLACK, INPUT_WIDTH);
        ITEM_W = new InputText("", FONT_XXS, BLACK, INPUT_WIDTH);
        ITEM_H = new InputText("", FONT_XXS, BLACK, INPUT_WIDTH);
        inputs.add(ITEM_ID);
        inputs.add(ITEM_X);
        inputs.add(ITEM_Y);
        inputs.add(ITEM_W);
        inputs.add(ITEM_H);
        ITEM_ID.setNumFlag(true);
        ITEM_X.setNumFlag(true);
        ITEM_Y.setNumFlag(true);
        ITEM_W.setNumFlag(true);
        ITEM_H.setNumFlag(true);
        components.add(TITLE);
        components.add(ADD_LINE);
        components.add(ADD_TOGGLE);
        components.add(GALLERY_BUTTON);
        components.add(SELECT_LINE);
        components.add(SELECT_TOGGLE);
        components.add(ITEM_STAT_LINE);
        components.add(ITEM_ID_LINE);
        components.add(ITEM_X_LINE);
        components.add(ITEM_Y_LINE);
        components.add(ITEM_W_LINE);
        components.add(ITEM_H_LINE);
        components.add(ITEM_ID);
        components.add(ITEM_X);
        components.add(ITEM_Y);
        components.add(ITEM_W);
        components.add(ITEM_H);
        stack.addBaseLayer(0);
        stack.layer(0).addComponent(components);
        stack.layer(0).addAdder(GALLERY_BUTTON, stack.layer(1));
        stack.layer(1).addComponent(GALLERY);
        stack.layer(1).addCloser(GALLERY.exit());
    }

    @Override
    public void screenResize() {
        //Positioning the components on the screen if the screen is resized
        UT_HTML.positioning(home.MAP_LINE, TITLE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 10);
        UT_HTML.positioning(TITLE, ADD_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 10);
        UT_HTML.positioning(ADD_LINE, ADD_TOGGLE, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ADD_TOGGLE, GALLERY_BUTTON, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ADD_LINE, SELECT_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 10);
        UT_HTML.positioning(SELECT_LINE, SELECT_TOGGLE, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(SELECT_LINE, ITEM_STAT_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        ITEM_STAT_LINE.addX(33);
        UT_HTML.positioning(ITEM_STAT_LINE,ITEM_ID_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 15);
        UT_HTML.positioning(ITEM_ID_LINE,ITEM_X_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT,15);
        UT_HTML.positioning(ITEM_X_LINE,ITEM_Y_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT,15);
        UT_HTML.positioning(ITEM_Y_LINE,ITEM_W_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 15);
        UT_HTML.positioning(ITEM_W_LINE, ITEM_H_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 15);
        UT_HTML.positioning(ITEM_ID_LINE, ITEM_ID, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ITEM_X_LINE, ITEM_X, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ITEM_Y_LINE, ITEM_Y, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ITEM_W_LINE, ITEM_W, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ITEM_H_LINE, ITEM_H, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.centerScreen(GALLERY);
    }

    @Override
    public void mouseClick() {
        boolean notBase = !stack.showing(0);
        int clickX = Main.WINDOW.MOUSE.clickX();
        int clickY = Main.WINDOW.MOUSE.clickY();
        int mapX = map.mapX(clickX);
        int mapY = map.mapY(clickY);
        stack.mouseClick();
        //Gallery Button
        if (stack.showing(1) && GALLERY.pickedID() > -1) {
            GALLERY_BUTTON.setImage(Main.MAP_IMAGE_MANAGER.ITEM_IMAGES[GALLERY.pickedID()]);
        }
        if (notBase) return;
        //Add Button
        if (ADD_TOGGLE.on()) {
            popToggles(ADD_TOGGLE, components);
            if (GALLERY.pickedID() > -1 && map.contains(clickX, clickY)) {
                map.itemmap().add(GALLERY.pickedID(), mapX, mapY);
            }
        }
        //Select Button
        if (SELECT_TOGGLE.on()) {
            popToggles(SELECT_TOGGLE, components);
            if (map.contains(clickX, clickY)) {
                if (pickedItem != null) pickedItem.hideEdges();
                pickedItem = map.itemmap().find(clickX, clickY, false);
                if (pickedItem != null) {
                    updateInput(pickedItem);
                    pickedItem.showEdges();
                }
            }
        }
    }

    @Override
    public void mousePress() {
        int pressX = Main.WINDOW.MOUSE.dragX1();
        int pressY = Main.WINDOW.MOUSE.dragY1();
        if (!map.contains(pressX, pressY) || !stack.showing(0)) return;
        int mapX = map.mapX(pressX);
        int mapY = map.mapY(pressY);
        if (SELECT_TOGGLE.on()) {
            sizingItem = map.itemmap().find(pressX, pressY, true);
            movingItem = map.itemmap().find(pressX, pressY, false);
            if (movingItem!=null)
                movingItem.vertices().setTranslationOrigin(mapX, mapY);
        }
    }

    @Override
    public void mouseDrag() {
        int mouseX = Main.WINDOW.MOUSE.dragX2();
        int mouseY = Main.WINDOW.MOUSE.dragY2();
        if (!map.contains(mouseX, mouseY) || !stack.showing(0)) return;
        int mapX = map.mapX(mouseX);
        int mapY = map.mapY(mouseY);
        if (SELECT_TOGGLE.on()) {
            if (sizingItem != null) {
                sizingItem.vertices().manipulate(mapX, mapY);
                updateInput(sizingItem);
            }
            else if (movingItem != null) {
                movingItem.vertices().translation(mapX, mapY);
                updateInput(movingItem);
            }
        }
    }

    @Override
    public void mouseRelease() {
        //Ending the moving/resizing of items
        if (SELECT_TOGGLE.on()) {
            if (sizingItem != null) {
                sizingItem = null;
            }
            if (movingItem != null) {
                movingItem = null;
            }
        }
    }

    @Override
    public void keyPress() {
        //Manipulating the layering of items
        if (Main.WINDOW.KEY.keyCode() == VK_UP) {
            if (pickedItem != null) {
                map.itemmap().adjustOrder(pickedItem, true);
            }
        }
        else if (Main.WINDOW.KEY.keyCode() == VK_DOWN) {
            if (pickedItem != null) {
                map.itemmap().adjustOrder(pickedItem, false);
            }
        }
    }

    @Override
    public void keyType() {
        ITEM_ID.keyType();
        ITEM_X.keyType();
        ITEM_Y.keyType();
        ITEM_W.keyType();
        ITEM_H.keyType();

        if (pickedItem != null) {
            pickedItem.setID(checkIntInput(ITEM_ID, pickedItem.getID(), 0, Main.MAP_IMAGE_MANAGER.ITEM_IMAGES.length - 1));
            pickedItem.setX(checkIntInput(ITEM_X, pickedItem.getX(), -pickedItem.getW(), map.getW()));
            pickedItem.setY(checkIntInput(ITEM_Y, pickedItem.getY(), -pickedItem.getH(), map.getH()));
            pickedItem.setW(checkIntInput(ITEM_W, pickedItem.getW(), 1, map.getW()));
            pickedItem.setH(checkIntInput(ITEM_H, pickedItem.getH(), 1, map.getH()));
        }
    }

    int checkIntInput(InputText input, int current, int min, int max) {
        int val = current;
        try {
            val = Integer.parseInt(input.getInput());
        }
        catch (NumberFormatException ignored) {}
        finally {
            val = Math.max(min, val);
            val = Math.min(max, val);
        }
        return val;
    }

    void updateInput(Item i) {
        ITEM_ID.setInput(String.valueOf(i.getID()));
        ITEM_X.setInput(String.valueOf(i.getX()));
        ITEM_Y.setInput(String.valueOf(i.getY()));
        ITEM_W.setInput(String.valueOf(i.getW()));
        ITEM_H.setInput(String.valueOf(i.getH()));
    }
}
