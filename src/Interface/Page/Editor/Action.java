package Interface.Page.Editor;

import Interface.Components.Text.InputText;
import Interface.Layering.Stacker;
import Interface.Page.Page;
import Maps.Map;
import Markers.Area;
import Markers.Drawing;
import Markers.Respond;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Action extends Page implements Drawing, Respond {
    ArrayList<Area> components;
    Stacker stack;
    Map map;
    Editor home;
    ArrayList<InputText> inputs;

    Action(Map m, Editor main) {
        components = new ArrayList<>();
        stack = new Stacker();
        inputs = new ArrayList<>();
        map = m;
        home = main;
    }

    void setMap(Map m) {
        map = m;
    }

    boolean activeInputs() {
        for (InputText i : inputs) {
            if (i.active())
                return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics2D G) {
        stack.draw(G);
    }
}