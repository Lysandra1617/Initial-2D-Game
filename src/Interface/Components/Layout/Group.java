package Interface.Components.Layout;

import Markers.Area;
import Markers.Drawing;
import Markers.Respond;
import Utilities.UT_HTML;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Group extends Area implements Respond, Drawing {
    static class RelComponent {
        Group group; //In order to reposition the element, access to the parent is needed
        Area component; //The component we're positioning
        boolean centerX, centerY; //true means that the component is to be centered in the respective position
        Integer cenOffX, cenOffY; //If these values aren't null, this is used to offset the position's x- and y-positions from the center
        Integer relPosX, relPosY; //If we're not centering the component, we need the component's relative x- and y-position

        RelComponent(Group g, Area a, boolean cx, boolean cy, Integer cxO, Integer cyO, Integer rxP, Integer ryP) {
            group = g;
            component = a;
            centerX = cx;
            centerY = cy;
            cenOffX = cxO;
            cenOffY = cyO;
            relPosX = rxP;
            relPosY = ryP;
        }

        void repositionY() {
            repositionY(group, component, relPosY, centerY, cenOffY);
        }

        void repositionX() {
            repositionX(group, component, relPosX, centerX, cenOffX);
        }

        static void repositionX(Group g, Area a, Integer rxPos, boolean center, Integer cxOffset) {
            if (rxPos != null) {
                if (rxPos > 0) a.setX(g.getX() + rxPos);
                else if (rxPos == 0) a.setX(g.getX());
                else a.setX(g.getX() + g.getW() + rxPos - a.getW());
            }
            else if (center) {
                UT_HTML.centerAreaX(a, g.getX(), g.getX() + g.getW());
                if (cxOffset != null) {
                    a.setX(a.getX() + cxOffset);
                }
            }
        }

        static void repositionY(Group g, Area a, Integer ryPos, boolean center, Integer cyOffset) {
            if (ryPos != null) {
                if (ryPos > 0) a.setY(g.getY() + ryPos);
                else if (ryPos == 0) a.setY(g.getY());
                else a.setY(g.getY() + g.getH() + ryPos - a.getH());
            }
            else if (center) {
                UT_HTML.centerAreaY(a, g.getY(), g.getY() + g.getH());
                if (cyOffset != null) {
                    a.setY(a.getY() + cyOffset);

                }
            }
        }
    }

    //All the components added to the group are stored here
    ArrayList<RelComponent> components;

    public Group() {
        components = new ArrayList<>();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        for (RelComponent c : components) {
            c.repositionX();
        }
    }

    @Override
    public void setY(int y) {
        this.y = y;
        for (RelComponent c : components) {
            c.repositionY();
        }
    }

    @Override
    public void mouseClick() {
        for (RelComponent c : components) {
            if (c.component instanceof Respond) {
                ((Respond) c.component).mouseClick();
            }
        }
    }

    @Override
    public void draw(Graphics2D G) {
        for (RelComponent c : components) {
            if (c.component instanceof Drawing) {
                ((Drawing) c.component).draw(G);
            }
        }
    }

    public void preposition(Area a, boolean centerX, boolean centerY, Integer cxOffset, Integer cyOffset, Integer rxPos, Integer ryPos) {
        RelComponent.repositionX(this, a, rxPos, centerX, cxOffset);
        RelComponent.repositionY(this, a, ryPos, centerY, cyOffset);
    }

    //Replacing a RelComponent's component with another. Kind of like if you wanted to substitute another area in
    public void replace(int index, Area a) {
        components.get(index).component = a;
    }

    //Adding a component via relative x- and y-positions
    public void add(Area a, Integer rxPos, Integer ryPos) {
        if (a.getH() > h || a.getW() > w) return;
        RelComponent c = new RelComponent(this, a, false, false, null, null, rxPos, ryPos);
        c.repositionX();
        c.repositionY();
        components.add(c);
    }

    //Adding a component to the center-x, can manipulate the component's relative y-position
    public void addCX(Area a, int cxOff, int ryPos) {
        if (a.getW() > w) return;
        RelComponent c = new RelComponent(this, a, true, false, cxOff, null, null, ryPos);
        c.repositionX();
        c.repositionY();
        components.add(c);
    }

    //Adding a component to the center-y, can manipulate the component's relative x-position
    public void addCY(Area a, int cyOff, int rxPos) {
        if (a.getH() > h) return;
        RelComponent c = new RelComponent(this, a, false, true, null, cyOff, rxPos, null);
        c.repositionX();
        c.repositionY();
        components.add(c);
    }

    //Adding a component to both the center-x and center-y
    public void addC(Area a) {
        if (a.getH() > h || a.getW() > w) return;
        RelComponent c = new RelComponent(this, a, true, true, null, null, null, null);
        c.repositionX();
        c.repositionY();
        components.add(c);
    }

    //Add a component, the relative x- and y-positions will be deduced
    public void add(Area a) {
        if (a.getW() > w || a.getH() > h) return;
        int offX = a.getX() - x;
        int offY = a.getY() - y;
        add(a, offX, offY);
    }

}
