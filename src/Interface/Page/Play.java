package Interface.Page;

import Maps.Game.GameMap;

import java.awt.Graphics2D;

public class Play extends Page {
    GameMap game;

    public Play() {
        game = new GameMap();
    }

    public void load(String s) {
        game = new GameMap(s);
    }

    @Override
    public void open() {
        screenResize();
    }

    @Override
    public void mouseClick() {
        NAVIGATION.mouseClick();
    }

    @Override
    public void keyPress() {
        game.keyPress();
    }

    @Override
    public void keyRelease() {
        game.keyRelease();
    }

    @Override
    public void update() {
        game.update();
    }

    @Override
    public void screenResize() {
        NAVIGATION.screenResize();
        game.screenResize();
    }

    @Override
    public void draw(Graphics2D G) {
        game.draw(G);
        NAVIGATION.draw(G);
    }
}
