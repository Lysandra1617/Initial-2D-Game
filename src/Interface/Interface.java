package Interface;

import Interface.Page.Editor.Editor;
import Interface.Page.Page;
import Interface.Page.Play;
import Interface.Page.Start;
import Interface.Page.View;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_Draw;

import java.awt.Graphics2D;

public class Interface implements Respond, Drawing {
    public final Start START;
    public final Editor EDITOR;
    public final View VIEW;
    public final Play PLAY;

    Page previousPage;
    Page onscreenPage;

    public Interface() {
        UT_Draw.init();
        START = new Start();
        EDITOR = new Editor();
        VIEW = new View();
        PLAY = new Play();
        previousPage = START;
        onscreenPage = START;
    }

    public void init() {
        onscreenPage.open();
    }

    //Go to a page
    public void go(Page p) {
        previousPage = onscreenPage;
        previousPage.close();
        onscreenPage = p;
        onscreenPage.open();
    }

    //Go back
    public void back() {
        onscreenPage.close();
        onscreenPage = previousPage;
        onscreenPage.open();
        if (onscreenPage == START) previousPage = START;
        if (onscreenPage == VIEW) previousPage = START;
        if (onscreenPage == EDITOR) previousPage = VIEW;
        if (onscreenPage == PLAY) previousPage = VIEW;
    }

    //The below methods (except for the last one) are used to send events to the current page
    @Override
    public void mouseMove() {
        if (onscreenPage != null) onscreenPage.mouseMove();
    }

    @Override
    public void mouseClick() {
        if (onscreenPage != null) onscreenPage.mouseClick();
    }

    @Override
    public void mousePress() {
        if (onscreenPage != null) onscreenPage.mousePress();
    }

    @Override
    public void mouseDrag() {
        if (onscreenPage != null) onscreenPage.mouseDrag();
    }

    @Override
    public void mouseRelease() {
        if (onscreenPage != null) onscreenPage.mouseRelease();
    }

    @Override
    public void screenResize() {
        if (onscreenPage != null) onscreenPage.screenResize();
    }

    @Override
    public void keyPress() {
        if (onscreenPage != null) onscreenPage.keyPress();
    }

    @Override
    public void keyRelease() {
        if (onscreenPage != null) onscreenPage.keyRelease();
    }

    @Override
    public void keyType() {
        if (onscreenPage != null) onscreenPage.keyType();
    }

    @Override
    public void update() {
        if (onscreenPage != null) onscreenPage.update();
    }

    @Override
    public void draw(Graphics2D G) {
        if (onscreenPage != null) onscreenPage.draw(G);
    }
}
