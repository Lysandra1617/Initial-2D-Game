package Interface.Layering;

import Markers.Drawing;
import Markers.Respond;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Stacker implements Respond, Drawing {
    HashMap<Integer, Layer> layers; //A layer is linked to an ID, all layers are here
    ArrayList<Layer> stackedLayers; //The layers that are drawn are here, and there are in order from last-opened to most-recently-opened

    public Stacker() {
        layers = new HashMap<>();
        stackedLayers = new ArrayList<>();
    }

    public Layer layer(Integer i) {
        //If the layer already exists, return it. If not, create it, then return it.
        if (!layers.containsKey(i)) {
            layers.put(i, new Layer(this));
        }
        return layers.get(i);
    }

    public Layer addBaseLayer(Integer i) {
        Layer l = layer(i);
        stackedLayers.add(0, l);
        return l;
    }

    public boolean showing(Integer i) {
        return stackedLayers.get(stackedLayers.size() - 1) == layers.get(i);
    }

    public void pop() {
        stackedLayers.remove(stackedLayers.size() - 1);
    }

    public void push(Layer l) {
        stackedLayers.add(l);
    }

    @Override
    public void mouseClick() {
        stackedLayers.get(stackedLayers.size() - 1).mouseClick();
    }

    @Override
    public void draw(Graphics2D G) {
        for (Layer l : stackedLayers) {
            l.draw(G);
        }
    }
}
