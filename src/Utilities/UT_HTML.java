package Utilities;

import Central.Main;
import Markers.Area;

public class UT_HTML {
    //What I learned from HTML was that the main and cross axes are perpendicular to each other
    public enum MAIN_AXIS {ABOVE, BELOW, BEHIND, FRONT}
    public enum CROSS_AXIS {LEFT, RIGHT, TOP, BOTTOM, MIDDLE}

    //Centers an area's x-position between the left and right positions
    public static void centerAreaX(Area a, int left, int right) {
        a.setX(centeredX(a, left, right));
    }

    //Centers an area's x-position respective to the fixed area
    public static void centerAreaX(Area fixed, Area a) {
        int middle = fixed.getX() + (fixed.getW() / 2);
        a.setX(middle - a.getW() / 2);
    }

    //Centers an area's y-position between the top and bottom positions
    public static void centerAreaY(Area a, int top, int bottom) {
        a.setY(centeredY(a, top, bottom));
    }

    //Centers an area's y-position respective to the fixed area
    public static void centerAreaY(Area fixed, Area a) {
        int middle = fixed.getY() + (fixed.getH() / 2);
        a.setY(middle - a.getH() / 2);
    }

    //Center an area on the screen
    public static void centerScreen(Area a) {
        centerAreaX(a, 0, Main.WINDOW.SCREEN.screenWidth());
        centerAreaY(a, 0, Main.WINDOW.SCREEN.screenHeight());
    }

    //Get the centered x-position
    public static int centeredX(Area a, int left, int right) {
        return ((left + right - a.getW()) / 2);
    }

    //Get the centered y-position
    public static int centeredY(Area a, int top, int bottom) {
        return ((top + bottom - a.getH()) / 2);
    }

    //Positions area b in respect to the fixed area, the main axis, the cross axis, and the gap for the main axis
    public static void positioning(final Area fixed, Area b, MAIN_AXIS main, CROSS_AXIS cross, int mainSpace) {
        int fixedX = fixed.getX();
        int fixedY = fixed.getY();
        int fixedW = fixed.getW();
        int fixedH = fixed.getH();
        int posW = b.getW();
        int posH = b.getH();

        //Main-Axis
        if (main == MAIN_AXIS.ABOVE)
            b.setY(fixedY - posH - mainSpace);
        else if (main == MAIN_AXIS.BELOW)
            b.setY(fixedY + fixedH + mainSpace);
        else if (main == MAIN_AXIS.BEHIND)
            b.setX(fixedX - mainSpace - posW);
        else if (main == MAIN_AXIS.FRONT)
            b.setX(fixedX + fixedW + mainSpace);

        //Cross-Axis
        if (cross == CROSS_AXIS.LEFT && (main == MAIN_AXIS.ABOVE || main == MAIN_AXIS.BELOW))
            b.setX(fixedX);
        else if (cross == CROSS_AXIS.RIGHT && (main == MAIN_AXIS.ABOVE || main == MAIN_AXIS.BELOW))
            b.setX(fixedX + fixedW - posW);
        else if (cross == CROSS_AXIS.TOP && (main == MAIN_AXIS.BEHIND || main == MAIN_AXIS.FRONT))
            b.setY(fixedY);
        else if (cross == CROSS_AXIS.BOTTOM && (main == MAIN_AXIS.BEHIND || main == MAIN_AXIS.FRONT))
            b.setY(fixedY + fixedH - posH);
        else if (cross == CROSS_AXIS.MIDDLE) {
            if (main == MAIN_AXIS.ABOVE || main == MAIN_AXIS.BELOW) {
                int relMidX = fixedX + (int) (fixedW * 0.5);
                int posDistMid = (int) (posW * 0.5);
                b.setX(relMidX - posDistMid);
            }
            else if (main == MAIN_AXIS.BEHIND || main == MAIN_AXIS.FRONT) {
                int relMidY = fixedY + (int) (fixedH * 0.5);
                int posDistMid = (int) (posH * 0.5);
                b.setY(relMidY - posDistMid);
            }
        }
    }

    //If you wanted to get the position so an area would be centered x-units from the left or right side of a container, this would be used
    public static int positionFromX(int containerX1, int containerW, int componentW, int distance) {
        int containerX2 = containerX1 + containerW;
        int position;

        if (distance > 0) position = containerX1 + distance;
        else if (distance < 0) position = containerX2 + distance - componentW;
        else position = containerX1;

        return position;
    }

    //If you wanted to get the position so an area would be centered y-units from the top or bottom side of a container, this would be used
    public static int positionFromY(int containerY1, int containerH, int componentH, int distance) {
        int containerY2 = containerY1 + containerH;
        int position;

        if (distance > 0) position = containerY1 + distance;
        else if (distance < 0) position = containerY2 + distance - componentH;
        else position = containerY1;

        return position;
    }
}
