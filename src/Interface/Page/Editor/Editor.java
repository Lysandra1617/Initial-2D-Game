package Interface.Page.Editor;

import Central.Main;
import Interface.Components.Buttons.LineButton;
import Interface.Components.Buttons.ToggleButton;
import Interface.Components.Text.InputText;
import Interface.Components.Text.LineText;
import Interface.Page.Page;
import Maps.Map;
import Markers.Area;
import Utilities.UT_HTML;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static Utilities.UT_Draw.drawImage;
import static Utilities.UT_Draw.drawRectangle;
import static Utilities.UT_HTML.CROSS_AXIS.LEFT;
import static Utilities.UT_HTML.CROSS_AXIS.MIDDLE;
import static Utilities.UT_HTML.MAIN_AXIS.FRONT;
import static java.awt.Color.BLACK;

public class Editor extends Page {
    final ArrayList<Action> ACTIONS;
    final TileAction TILE_ACTION;
    final ItemAction ITEM_ACTION;
    Action action;
    int actionIndex;
    Map map;
    final int CHANGE;
    int changeX;
    int changeY;
    final LineText MAP_LINE;
    final InputText MAP_INPUT;
    final LineButton SAVE;
    final LineButton RESET;
    boolean existing;

    public Editor() {
        map = new Map();
        existing = false;
        ACTIONS = new ArrayList<>();
        TILE_ACTION = new TileAction(map, this);
        ITEM_ACTION = new ItemAction(map, this);
        ACTIONS.add(TILE_ACTION);
        ACTIONS.add(ITEM_ACTION);
        actionIndex = 0;
        action = ACTIONS.get(actionIndex);
        CHANGE = 12;
        changeX = 0;
        changeY = 0;
        MAP_LINE = new LineText("Map", FONT_M, BLACK);
        MAP_INPUT = new InputText(map.getName(), FONT_MS, BLACK, 250);
        MAP_INPUT.setFileFlag(true);
        SAVE = new LineButton("SAVE", FONT_XS, BLACK);
        SAVE.overrideImageW(20);
        SAVE.overrideImageH(20);
        RESET = new LineButton("RESET", FONT_XS, BLACK);
        RESET.overrideImageW(20);
        RESET.overrideImageH(20);
    }

    @Override
    public void open() {
         frameDimensions(1000, 600);
         screenResize();
    }

    @Override
    public void close() {
        map = new Map();
        TILE_ACTION.setMap(map);
        ITEM_ACTION.setMap(map);
        MAP_INPUT.setInput("");
        existing = false;
    }

    public void load(String mapName) {
        map = new Map(mapName);
        TILE_ACTION.setMap(map); //In order for the actions to actually take place, the map needs to be set
        ITEM_ACTION.setMap(map);
        MAP_INPUT.setInput(mapName);
        existing = true; //For saving
        screenResize();
    }

    @Override
    public void screenResize() {
        NAVIGATION.screenResize();
        SAVE.setX(10);
        SAVE.setY(NAVIGATION.getH()+10);
        UT_HTML.positioning(SAVE, RESET, FRONT, MIDDLE, 0);
        UT_HTML.positioning(SAVE, MAP_LINE, UT_HTML.MAIN_AXIS.BELOW, LEFT, 0);
        UT_HTML.positioning(MAP_LINE, MAP_INPUT, FRONT, MIDDLE, 10);
        for (Action a : ACTIONS) a.screenResize();
        final int SCREEN_WIDTH = Main.WINDOW.SCREEN.screenWidth();
        map.setX(SCREEN_WIDTH/2);
    }

    @Override
    public void mouseClick() {
        NAVIGATION.mouseClick();
        action.mouseClick();
        MAP_INPUT.mouseClick();
        SAVE.mouseClick();
        RESET.mouseClick();
        if (SAVE.on()) {
            if (existing)
                map.rename(MAP_INPUT.getInput());
            else
                map.setName(MAP_INPUT.getInput());
            if (map.save()) {
                existing = true;
            }
        }
        else if (RESET.on()) {
            if (existing) map.reset();
            else map.erase();
        }
    }

    @Override
    public void mousePress() {
        action.mousePress();
    }

    @Override
    public void mouseDrag() {
        action.mouseDrag();
    }

    @Override
    public void mouseRelease() {
        action.mouseRelease();
    }

    @Override
    public void mouseMove() {
        SAVE.mouseMove();
        RESET.mouseMove();
    }

    @Override
    public void keyPress() {
        action.keyPress();
        //LEFT -> Moves the map closer to x = 0
        //RIGHT -> Moves the map closer to the horizontal end of the map
        //UP -> Moves the map closer to y = 0
        //DOWN -> Moves the map closer to the vertical end of the map
        //[ -> Go to the previous action set
        //] -> Go to the next action set
        //G -> Toggling the grid on the map
        switch (Main.WINDOW.KEY.keyCode()) {
            case KeyEvent.VK_OPEN_BRACKET -> {
                if (MAP_INPUT.active() || ITEM_ACTION.activeInputs()) return;
                actionIndex--;
                if (actionIndex < 0) actionIndex = ACTIONS.size() - 1;
                action = ACTIONS.get(actionIndex);
            }
            case KeyEvent.VK_CLOSE_BRACKET -> {
                if (MAP_INPUT.active() || ITEM_ACTION.activeInputs()) return;
                actionIndex++;
                if (actionIndex >= ACTIONS.size()) actionIndex = 0;
                action = ACTIONS.get(actionIndex);
            }
            case KeyEvent.VK_LEFT -> {
                changeX = Math.min(0, changeX + CHANGE);
                map.setMapAdjustX(changeX);
            }
            case KeyEvent.VK_DOWN -> {
                changeY = Math.max(-map.getH(), changeY - CHANGE);
                map.setMapAdjustY(changeY);
            }
            case KeyEvent.VK_UP -> {
                changeY = Math.min(0, changeY + CHANGE);
                map.setMapAdjustY(changeY);
            }
            case KeyEvent.VK_RIGHT -> {
                changeX = Math.max(-map.getW(), changeX - CHANGE);
                map.setMapAdjustX(changeX);
            }
            case KeyEvent.VK_G -> {
                if (MAP_INPUT.active() || ITEM_ACTION.activeInputs()) return;
                map.switchGrid();
            }
        }
    }

    @Override
    public void keyType() {
        MAP_INPUT.keyType();
        action.keyType();
    }

    @Override
    public void draw(Graphics2D G) {
        map.draw(G);
        drawRectangle(0,0,map.getX(),Main.WINDOW.SCREEN.screenHeight(),true,Color.WHITE,G);
        MAP_LINE.draw(G);
        MAP_INPUT.draw(G);
        SAVE.draw(G);
        RESET.draw(G);
        action.draw(G);
        if (action == TILE_ACTION) {
            drawImage(TILE_ACTION.GALLERY_BUTTON.getX(), TILE_ACTION.GALLERY_BUTTON.getY(), TILE_ACTION.GALLERY_BUTTON.getW(), TILE_ACTION.GALLERY_BUTTON.getH(), Main.G_IMAGE_MANAGER.TRANSP_BOX, G);
        }
        else if (action == ITEM_ACTION) {
            drawImage(ITEM_ACTION.GALLERY_BUTTON.getX(), ITEM_ACTION.GALLERY_BUTTON.getY(), ITEM_ACTION.GALLERY_BUTTON.getW(), ITEM_ACTION.GALLERY_BUTTON.getH(), Main.G_IMAGE_MANAGER.TRANSP_BOX, G);
        }
        NAVIGATION.draw(G);
    }

    //Currently, only one toggle can be on at a time
    static void popToggles(ToggleButton exception, ArrayList<Area> toggles) {
        if (exception.clicked()) turnoffToggles(exception, toggles);
    }

    //Turns off all other toggles
    static void turnoffToggles(ToggleButton exception, ArrayList<Area> toggles) {
        for (Area toggle : toggles) {
            if (toggle instanceof ToggleButton) {
                if (toggle == exception)
                    continue;
                ((ToggleButton)toggle).turnoff();
            }
        }
    }
}
