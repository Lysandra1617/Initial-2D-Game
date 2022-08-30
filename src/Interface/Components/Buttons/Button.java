package Interface.Components.Buttons;

import Central.Main;
import Markers.Area;
import Markers.Drawing;
import Markers.Respond;

import javax.swing.Timer;

public abstract class Button extends Area implements Drawing, Respond {
    boolean on;
    boolean hover;
    boolean clicked;

    Timer stateTimer; //Resets the button from on = true to on = false
    Timer clickTimer; //Resets the button from clicked = true to clicked = false

    public Button() {
        stateTimer = new Timer(150, e->stateReset());
        clickTimer = new Timer(150, e->clickReset());
    }

    public boolean on() {
        return on;
    }

    public boolean hover() {
        return hover;
    }

    public boolean clicked() {
        return clicked;
    }

    public void turnoff() {
        on = false;
    }

    public void hoverOff() {
        hover = false;
    }

    void stateReset() {
        on = false;
        if (stateTimer != null)
            stateTimer.stop();
    }

    void clickReset() {
        clicked = false;
        if (clickTimer != null)
            clickTimer.stop();
    }

    @Override
    public void mouseClick() {
        if (contains(Main.WINDOW.MOUSE.clickX(), Main.WINDOW.MOUSE.clickY())) {
            on = true;
            clicked = true;
            clickTimer.restart();
            stateTimer.restart();
        }
    }

    @Override
    public void mouseMove() {
        hover = contains(Main.WINDOW.MOUSE.hoverX(), Main.WINDOW.MOUSE.hoverY());
    }
}
