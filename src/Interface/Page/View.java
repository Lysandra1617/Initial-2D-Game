package Interface.Page;

import Central.Main;
import Interface.Components.Buttons.Button;
import Interface.Components.Buttons.ImageButton;
import Interface.Components.Buttons.LineButton;
import Interface.Components.Text.LineText;
import Interface.Layering.Layer;
import Interface.Layering.Stacker;
import Markers.Area;
import Utilities.UT_Draw;
import Utilities.UT_File;
import Utilities.UT_HTML;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

import static Utilities.UT_HTML.CROSS_AXIS.LEFT;
import static Utilities.UT_HTML.CROSS_AXIS.MIDDLE;
import static Utilities.UT_HTML.MAIN_AXIS.BELOW;
import static Utilities.UT_HTML.MAIN_AXIS.FRONT;

public class View extends Page {
    final Stacker STACK;
    final LineText TITLE;
    final LineButton CREATE;
    final LineText SHOWING;
    final ImageButton UP;
    final ImageButton DOWN;
    ArrayList<LineButton> savedMaps;
    ArrayList<LineButton> shownMaps;
    int shownMapStart; //What map name do we start listing from
    int shownMapSectionY; //The y-position of the list of saved maps
    final int SHOWN_MAP_H; //The height of the list of saved maps
    final LineButton PLAY;
    final LineButton EDIT;
    final LineButton DELETE;
    LineButton clickedMap;
    boolean autoClose; //For closing the actions that open when you click a saved map

    public View() {
        STACK = new Stacker();
        TITLE = new LineText("SAVED MAPS", FONT_M, PURE_WHITE);
        CREATE = new LineButton("Create Map", FONT_XS, PURE_WHITE);
        CREATE.overrideImageW(15);
        CREATE.overrideImageH(15);
        SHOWING = new LineText("SHOWING MAPS:", FONT_MS, PURE_WHITE);
        UP = new ImageButton();
        UP.setW(50);
        UP.setH(50);
        UP.setImage(Main.G_IMAGE_MANAGER.SHORT_U);
        DOWN = new ImageButton();
        DOWN.setW(50);
        DOWN.setH(50);
        DOWN.setImage(Main.G_IMAGE_MANAGER.SHORT_D);
        PLAY = new LineButton("PLAY", FONT_MS, PURE_WHITE);
        EDIT = new LineButton("EDIT", FONT_MS, PURE_WHITE);
        DELETE = new LineButton("DELETE", FONT_MS, PURE_WHITE);
        PLAY.overrideImageW(30);
        PLAY.overrideImageH(30);
        EDIT.overrideImageW(30);
        EDIT.overrideImageH(30);
        DELETE.overrideImageW(30);
        DELETE.overrideImageH(30);
        SHOWN_MAP_H = 350;
        shownMapStart = 0;
        savedMaps = new ArrayList<>();
        shownMaps = new ArrayList<>();
        STACK.addBaseLayer(0).addComponent(TITLE).addComponent(CREATE).addComponent(SHOWING).addComponent(UP).addComponent(DOWN);
        STACK.layer(1).addComponent(PLAY).addComponent(EDIT).addComponent(DELETE);
    }

    @Override
    public void open() {
        frameDimensions(700, 450);
        loadMaps(); //Load all the saved maps
        screenResize();
        figureShownMaps(); //Figure out which maps of the saved maps can appear on-screen
        registerMaps(); //Makes the maps on screen respond-able
    }

    @Override
    public void close() {
        unregisterMaps(true);
        forceClose();
        PLAY.hoverOff();
        EDIT.hoverOff();
        DELETE.hoverOff();
        CREATE.hoverOff();
    }

    @Override
    public void screenResize() {
        NAVIGATION.screenResize();
        COPYRIGHT.screenResize();
        UT_HTML.positioning(NAVIGATION, TITLE, BELOW, LEFT, 10);
        TITLE.setX(10);
        UT_HTML.positioning(TITLE, CREATE, BELOW, LEFT, 10);
        UT_HTML.positioning(CREATE, SHOWING, BELOW, LEFT, 0);
        shownMapSectionY = SHOWING.getY() + 5; //Important for positioning the shown maps
        SHOWING.addY(15); //Just so it looks nice
        UT_HTML.positioning(SHOWING, UP, FRONT, MIDDLE, 0);
        UT_HTML.positioning(UP, DOWN, FRONT, MIDDLE, 0);
    }

    @Override
    public void mouseClick() {
        NAVIGATION.mouseClick();
        STACK.mouseClick();
        for (LineButton b : shownMaps) {
            if (b.on()) {
                b.disableImage(); //So the little image that appears when hovering on a LineButton doesn't show and block view to the actions
                orientActions(b); //Moving the actions near the clicked map
                clickedMap = b; //Remembering the clicked map
            }
        }
        if (savedMaps.size() != shownMaps.size()) { //If there's more maps to see
            if (DOWN.on()) {
                int currentStart = shownMapStart;
                shownMapStart = Math.min(savedMaps.size() - 1, currentStart + 1);
                if (shownMapStart != currentStart) updateShownMaps();
            } else if (UP.on()) {
                int pre = shownMapStart;
                shownMapStart = Math.max(0, shownMapStart - 1);
                if (pre != shownMapStart) updateShownMaps();
            }
        }
        else if (CREATE.on()) {
            Main.INTERFACE.go(Main.INTERFACE.EDITOR);
        }
        if (STACK.showing(1) && clickedMap != null) {
            if (PLAY.on()) {
                Main.INTERFACE.PLAY.load(clickedMap.getText());
                Main.INTERFACE.go(Main.INTERFACE.PLAY);
            }
            else if (EDIT.on()) {
                Main.INTERFACE.EDITOR.load(clickedMap.getText());
                Main.INTERFACE.go(Main.INTERFACE.EDITOR);
            }
            else if (DELETE.on()) {
                (new Maps.Map(clickedMap.getText())).delete();
                forceClose();
                updateShownMaps();
            }
        }
    }

    @Override
    public void mouseMove() {
        CREATE.mouseMove();
        if (STACK.showing(1)) {
            PLAY.mouseMove();
            EDIT.mouseMove();
            DELETE.mouseMove();
            autoClose();
        }
        for (LineButton b : shownMaps) b.mouseMove();
    }

    @Override
    public void draw(Graphics2D G) {
        NAVIGATION.draw(G);
        UT_Draw.drawRectangle(0, NAVIGATION.getH(), Main.WINDOW.SCREEN.screenWidth(), SHOWING.getY() - NAVIGATION.getH(), true, DARK_GRAY, G);
        UT_Draw.drawRectangle(0, shownMapSectionY, Main.WINDOW.SCREEN.screenWidth(), Main.WINDOW.SCREEN.screenHeight() - shownMapSectionY, true, LIGHT_GRAY, G);
        if (clickedMap != null) {
            clickedMap.getLabel().drawShadow(G, Page.GRAY, 1, 3);
        }
        for (LineButton l : shownMaps) {
            l.draw(G);
        }
        if (PLAY.hover()) {
            PLAY.getLabel().drawShadow(G, Page.GRAY, 0, 3);
        }
        else if (EDIT.hover()) EDIT.getLabel().drawShadow(G, Page.GRAY, 0, 3);
        else if (DELETE.hover()) DELETE.getLabel().drawShadow(G, Page.GRAY, 0, 3);
        TITLE.drawShadow(G, Color.BLACK, 0, 4);
        TITLE.drawShadow(G, Page.CHARCOAL, 0, 3);
        STACK.draw(G);
        if (CREATE.hover()) {
            CREATE.getLabel().drawShadow(G, Page.BLUE, 0, 0);
        }
        COPYRIGHT.draw(G);
    }

    void unregisterMaps(boolean all) {
        //In essence, we're removing the LineButtons from the base layer
        Iterator<HashMap.Entry<Button, Layer>> iter = STACK.layer(0).adders().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Button, Layer> entry = iter.next();
            if (entry.getKey() instanceof LineButton) {
                if (all) {
                    if (savedMaps.contains(entry.getKey())) {
                        savedMaps.remove(entry.getKey());
                        iter.remove();
                    }
                } else {
                    if (shownMaps.contains(entry.getKey())) {
                        shownMaps.remove(entry.getKey());
                        iter.remove();
                    }
                }
            }
        }
    }

    void registerMaps() {
        //STACK.layer(1) contains the actions (e.g. PLAY, EDIT, DELETE)
        for (Button b : shownMaps) {
            STACK.layer(0).addAdder(b, STACK.layer(1));
        }
    }

    void loadMaps() {
        savedMaps.clear();
        String[] maps = UT_File.directoryFiles("src\\Resources\\Saved_Maps\\");
        if (maps != null) {
            for (String map : maps) {
                LineButton l = new LineButton(map.substring(0, map.length() - 4), FONT_S, PURE_WHITE);
                l.overrideImageW(30);
                l.overrideImageH(30);
                savedMaps.add(l);
            }
        }
        savedMaps.sort(Comparator.comparingInt(LineButton::labelSize).reversed());
    }

    //Basically just showing as much map-lines as the given height will allow
    void figureShownMaps() {
        shownMaps.clear();
        if (savedMaps.isEmpty()) return;
        final int LINE_HEIGHT = savedMaps.get(0).getH();
        final int SAVED_MAP_COUNT = savedMaps.size();
        final int SPACING = 7;
        int currentSectionHeight;

        for (int i = shownMapStart; i < SAVED_MAP_COUNT; i++) {
            currentSectionHeight = (LINE_HEIGHT * (i + 1 - shownMapStart)) + ((i - shownMapStart) * SPACING) + SPACING;
            if (currentSectionHeight >= SHOWN_MAP_H) {
                break;
            }
            else {
                shownMaps.add(savedMaps.get(i));
            }
        }

        final int SHOWN_MAP_COUNT = shownMaps.size();
        for (int i = 0; i < SHOWN_MAP_COUNT; i++) {
            UT_HTML.positioning((i == 0) ? SHOWING : shownMaps.get(i - 1), shownMaps.get(i), BELOW, LEFT, (i == 0) ? SPACING + 10 : SPACING);
        }
    }

    void updateShownMaps() {
        unregisterMaps(false);
        loadMaps();
        figureShownMaps();
        registerMaps();
    }

    //The actions should appear near the clicked map
    void orientActions(Area a) {
        PLAY.setX(a.right() + 10);
        PLAY.setY(a.getY());
        UT_HTML.positioning(PLAY, EDIT, BELOW, LEFT, 0);
        UT_HTML.positioning(EDIT, DELETE, BELOW, LEFT, 0);
    }

    //If you hover out of the action region, it should just disappear. Previously, you had to click a button to close out of it, and that was bothersome
    void autoClose() {
        int left = PLAY.left();
        int right = DELETE.right();
        int top = PLAY.top();
        int bottom = DELETE.bottom();
        int hoverX = Main.WINDOW.MOUSE.hoverX();
        int hoverY = Main.WINDOW.MOUSE.hoverY();
        boolean active = hoverX >= left && hoverX <= right && hoverY >= top && hoverY <= bottom;
        if (autoClose) {
            if (!active) {
                STACK.pop();
                autoClose = false;
                if (clickedMap != null) {
                    clickedMap.enableImage();
                    clickedMap = null;
                }
            }
        }
        else {
            autoClose = active;
        }
    }

    //Closes the actions that pop up when a map is clicked
    void forceClose() {
        if (STACK.showing(1)) {
            STACK.pop();
            if (clickedMap != null) {
                clickedMap.enableImage();
                clickedMap = null;
            }
            autoClose = false;
        }
    }
}
