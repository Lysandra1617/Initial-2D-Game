package Interface.Layering;

import Markers.Area;
import Markers.Drawing;
import Markers.Respond;

import Interface.Components.Buttons.Button;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Layer implements Respond, Drawing {
    final Stacker STACK; //For the pop and push methods, the Layer object must know where it stems from
    ArrayList<Area> components; //All the components on the layer
    ArrayList<Button> closers; //Buttons that close the layer
    HashMap<Button, Layer> adders; //Buttons that open another layer, the layer is linked via HashMap

    Layer(Stacker stacker) {
        STACK = stacker;
        components = new ArrayList<>();
        closers = new ArrayList<>();
        adders = new HashMap<>();
    }

    public ArrayList<Area> components() {
        return components;
    }

    public Layer addComponent(Area a) {
        components.add(a);
        return this;
    }

    public Layer addComponent(ArrayList<Area> as) {
        components.addAll(as);
        return this;
    }

    public Layer addCloser(Button b) {
        closers.add(b);
        return this;
    }

    public Layer addAdder(Button b, Layer l) {
        adders.put(b, l);
        return this;
    }

    public HashMap<Button, Layer> adders() {
        return adders;
    }

    @Override
    public void mouseClick() {
        //Checking buttons that close the layer
        for (Button b : closers) {
            b.mouseClick();
            if (b.on()) {
                STACK.pop(); //Popping this layer
                return;
            }
        }

        //Checking buttons that open another layer
        for (HashMap.Entry<Button, Layer> entry : adders.entrySet()) {
            entry.getKey().mouseClick();
            if (entry.getKey().on()) {
                STACK.push(entry.getValue()); //Pushing a new layer
                return;
            }
        }

        //If we're not closing or adding another layer, respond to other components
        for (Area a : components) {
            if (a instanceof Respond) {
                ((Respond) a).mouseClick();
            }
        }
    }

    @Override
    public void draw(Graphics2D G) {
        for (Area a : components) {
            if (a instanceof Drawing) {
                ((Drawing) a).draw(G);
            }
        }
    }
}
