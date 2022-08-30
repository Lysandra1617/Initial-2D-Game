package Interface.Page.Editor;

import Central.Main;
import Interface.Components.Buttons.ImageButton;
import Interface.Components.Buttons.ToggleButton;
import Interface.Components.Layout.Gallery;
import Interface.Components.Text.LineText;
import Maps.Map;
import Markers.Drawing;
import Utilities.UT_HTML;

import static java.awt.Color.BLACK;

public class TileAction extends Action implements Drawing {
    LineText TITLE;
    LineText ADD_LINE;
    ToggleButton ADD_TOGGLE;
    ImageButton GALLERY_BUTTON;
    Gallery GALLERY;
    LineText DELETE_LINE;
    ToggleButton DELETE_TOGGLE;

    public TileAction(Map m, Editor main) {
        super(m, main);
        TITLE = new LineText("TILES", FONT_S, BLACK);
        ADD_LINE = new LineText("Add Tile", FONT_XS, BLACK);
        ADD_TOGGLE = new ToggleButton();
        GALLERY_BUTTON = new ImageButton();
        GALLERY_BUTTON.setImage(Main.G_IMAGE_MANAGER.TRANSP_BOX);
        GALLERY_BUTTON.setW(20);
        GALLERY_BUTTON.setH(20);
        GALLERY = new Gallery(3,3,Main.MAP_IMAGE_MANAGER.TILE_IMAGES);
        DELETE_LINE = new LineText("Delete Tile", FONT_XS, BLACK);
        DELETE_TOGGLE = new ToggleButton();
        components.add(TITLE);
        components.add(ADD_LINE);
        components.add(ADD_TOGGLE);
        components.add(GALLERY_BUTTON);
        components.add(DELETE_LINE);
        components.add(DELETE_TOGGLE);
        stack.addBaseLayer(0);
        stack.layer(0).addComponent(components);
        stack.layer(0).addAdder(GALLERY_BUTTON, stack.layer(1));
        stack.layer(1).addComponent(GALLERY);
        stack.layer(1).addCloser(GALLERY.exit());
    }

    @Override
    public void screenResize() {
        UT_HTML.positioning(home.MAP_LINE, TITLE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 10);
        UT_HTML.positioning(TITLE, ADD_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 10);
        UT_HTML.positioning(ADD_LINE, ADD_TOGGLE, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ADD_TOGGLE, GALLERY_BUTTON, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.positioning(ADD_LINE, DELETE_LINE, UT_HTML.MAIN_AXIS.BELOW, UT_HTML.CROSS_AXIS.LEFT, 10);
        UT_HTML.positioning(DELETE_LINE, DELETE_TOGGLE, UT_HTML.MAIN_AXIS.FRONT, UT_HTML.CROSS_AXIS.MIDDLE, 10);
        UT_HTML.centerScreen(GALLERY);
    }

    @Override
    public void mouseClick() {
        boolean notBase = !stack.showing(0);
        int clickX = Main.WINDOW.MOUSE.clickX();
        int clickY = Main.WINDOW.MOUSE.clickY();
        int mapRow = map.mapRow(clickY);
        int mapCol = map.mapCol(clickX);
        boolean mapClicked = map.contains(clickX, clickY);
        boolean goodRC = mapRow > -1 && mapRow < map.rows() && mapCol > -1 && mapCol < map.columns();
        stack.mouseClick();
        //Gallery Button
        if (stack.showing(1) && GALLERY.pickedID() > -1) {
            GALLERY_BUTTON.setImage(Main.MAP_IMAGE_MANAGER.TILE_IMAGES[GALLERY.pickedID()]);
        }
        if (notBase) return;
        //Adding Tile
        if (ADD_TOGGLE.on()) {
            Editor.popToggles(ADD_TOGGLE, components);
            if (stack.showing(0) && mapClicked && goodRC && GALLERY.pickedID() > -1) {
                map.tilemap().add(GALLERY.pickedID(), mapRow, mapCol);
            }
        }
        //Deleting Tile
        if (DELETE_TOGGLE.on()) {
            Editor.popToggles(DELETE_TOGGLE, components);
            if (stack.showing(0) && mapClicked && goodRC) {
                map.tilemap().delete(mapRow, mapCol);
            }
        }
    }

    @Override
    public void mouseDrag() {
        //Makes it easier to add/delete tiles
        int dragX = Main.WINDOW.MOUSE.dragX2();
        int dragY = Main.WINDOW.MOUSE.dragY2();
        int mapRow = map.mapRow(dragY);
        int mapCol = map.mapCol(dragX);
        boolean goodRC = mapRow > -1 && mapRow < map.rows() && mapCol > -1 && mapCol < map.columns();

        if (ADD_TOGGLE.on()) {
            if (GALLERY.pickedID() > -1) {
                if (goodRC) {
                    if (map.tilemap().tiles()[mapRow][mapCol] == null || map.tilemap().tiles()[mapRow][mapCol].getID() != GALLERY.pickedID()) {
                        map.tilemap().add(GALLERY.pickedID(), mapRow, mapCol);
                    }
                }
            }
        }
        else if (DELETE_TOGGLE.on()) {
            if (goodRC) map.tilemap().delete(mapRow, mapCol);
        }
    }
}
