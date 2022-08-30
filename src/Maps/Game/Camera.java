package Maps.Game;

import Central.Main;
import Maps.Game.Entity.Entity;
import Maps.Units.Tile;
import Markers.Area;
import Markers.Respond;

public class Camera implements Respond {

    //What the camera is focusing on
    Focus focus;

    //Determines what parts of the map are drawn
    DrawnMap drawnMap = new DrawnMap();

    //Used for more fluid transitions
    Transition transition = new Transition();

    //Tiles on a map have an x and y value that correlates to its position on the map, not the screen. To get the tile
    //to properly draw on the screen, an offset is needed for both x and y
    int screenOffsetX = 0;
    int screenOffsetY = 0;

    //Transitions will take place 100px-300px from the screen boundaries
    int transitionRangeX = 300;
    int transitionRangeY = 100;
    boolean inTransitionX = false;
    boolean inTransitionY = false;

    @Override
    public synchronized void screenResize() {
        int screenRows = (int) Math.ceil(1.0 * Main.WINDOW.SCREEN.screenHeight() / Tile.TILE_HEIGHT);
        int screenCols = (int) Math.ceil(1.0 * Main.WINDOW.SCREEN.screenWidth() / Tile.TILE_WIDTH);

        drawnMap.setScreenRows(screenRows);
        drawnMap.setScreenColumns(screenCols);
    }

    public void setFocus(Entity e) {
        if (focus == null)
            focus = new Focus(e);
        else
            focus.e = e;
    }

    @Override
    public void update() {
        if (inTransitionX) {
            transitionX();
        }
        else {
            int screenW = Main.WINDOW.SCREEN.screenWidth();
            int tileW = Tile.TILE_WIDTH;
            int focusCenterX = focus.middleX();

            //If the focus is moving left
            if (focus.movingLeft()) {

                //Find the map section that the focus is currently in
                drawnMap.setMapSectionX(focusCenterX / screenW);

                //The screen offset should be determined by the first column drawn on screen
                screenOffsetX = -drawnMap.drawColumnStart * tileW;

                //The left of the current map section
                int currMapSectionLeftX = drawnMap.mapSectionNumber[1] * screenW;

                //I know the name is long, but there is no other way for me to name it
                //This is the distance from the focus's center x to the left of the map section
                int focusDistanceToMapSectionLeftX = focusCenterX - currMapSectionLeftX;

                if (focusDistanceToMapSectionLeftX <= transitionRangeX && focusDistanceToMapSectionLeftX >= 0) {
                    inTransitionX = true;

                    //Since we are moving left, we increase the columns on the left end of the map in order to manipulate the offset to create a
                    //fluid-like transition
                    drawnMap.expandColumns(-1);

                    transition.markTransitionOffsetsX(screenOffsetX, -drawnMap.drawColumnStart * tileW);
                }
            }
            //If the focus is moving right
            else if (focus.movingRight()) {

                //Finding the map section that the focus is currently in
                drawnMap.setMapSectionX(focusCenterX / screenW);

                //The screen offset should be determined by the first column drawn on screen
                screenOffsetX = -drawnMap.drawColumnStart * tileW;

                //The x-position of the current map section's right-hand side
                int currMapSectionRightX = (drawnMap.mapSectionNumber[1] + 1) * screenW;

                //How far the focus is from the right of the map section
                int focusDistanceToMapSectionRightX = currMapSectionRightX - focus.middleX();

                //If the focus is within range of the transition boundary
                if (focusDistanceToMapSectionRightX <= transitionRangeX && focusDistanceToMapSectionRightX >= 0) {
                    inTransitionX = true;

                    transition.markTransitionOffsetsX(screenOffsetX, -drawnMap.drawColumnEnd * tileW);

                    //Moving to the right, increasing the columns on the right end
                    drawnMap.expandColumns(1);
                }
            }
        }

        if (inTransitionY) {
            transitionY();
        }
        else {
            int screenH = Main.WINDOW.SCREEN.screenHeight();
            int tileH = Tile.TILE_HEIGHT;
            int focusTopY = focus.top();

            //Focus moving up
            if (focus.movingUp()) {

                drawnMap.setMapSectionY(focusTopY / screenH);

                screenOffsetY = -drawnMap.drawRowStart * tileH;

                int currMapSectionTopY = drawnMap.mapSectionNumber[0] * screenH;
                int focusDistanceToMapSectionTopY = focusTopY - currMapSectionTopY;

                if (focusDistanceToMapSectionTopY <= transitionRangeY && focusDistanceToMapSectionTopY >= 0) {
                    inTransitionY = true;
                    drawnMap.expandRows(-1);
                    transition.markTransitionOffsetsY(screenOffsetY, -drawnMap.drawRowStart * tileH);
                }
            }
            //If the focus is moving right
            else if (focus.movingDown()) {

                drawnMap.setMapSectionY(focusTopY / screenH);

                screenOffsetY = -drawnMap.drawRowStart * tileH;

                int currMapSectionBottomY = (drawnMap.mapSectionNumber[0] + 1) * screenH;
                int focusDistanceToMapSectionBottomY = currMapSectionBottomY - focusTopY;

                if (focusDistanceToMapSectionBottomY <= transitionRangeY && focusDistanceToMapSectionBottomY >= 0) {
                    inTransitionY = true;
                    transition.markTransitionOffsetsY(screenOffsetY, -drawnMap.drawRowEnd * tileH);
                    drawnMap.expandRows(1);
                }
            }
        }
    }

    public void transitionX() {

        int screenW = Main.WINDOW.SCREEN.screenWidth();
        int focusCenterX = focus.middleX();

        //Moving right
        if (screenOffsetX > transition.nextScreenOffsetX) {

            int currMapSectionRightX = (drawnMap.mapSectionNumber[1] + 1) * screenW;
            int focusDistanceToMapSectionRightX = currMapSectionRightX - focusCenterX;

            //If still within transition bounds
            if (focusDistanceToMapSectionRightX <= transitionRangeX && focusDistanceToMapSectionRightX >= 0) {

                //This variable will have variables from [0.0 to 1.0] and it basically means how deep the focus is in the transition range/boundary
                double normalizedFocusPosition = 1.0 * (focusCenterX - (currMapSectionRightX - transitionRangeX)) / transitionRangeX;

                //The screen offset depends on where the focus is in the transition range
                screenOffsetX = (int) (transition.currScreenOffsetX - normalizedFocusPosition * transition.offsetChangeX);
            }
            else {
                inTransitionX = false;
            }
        }

        //Moving left
        else if (screenOffsetX < transition.nextScreenOffsetX) {

            int currMapSectionLeftX = drawnMap.mapSectionNumber[1] * screenW;
            int focusDistanceToMapSectionLeftX = focusCenterX - currMapSectionLeftX;

            //If within transition bounds
            if (focusDistanceToMapSectionLeftX <= transitionRangeX && focusDistanceToMapSectionLeftX >= 0) {

                //Look above for comments
                double normalizedFocusPosition = 1.0 * (currMapSectionLeftX + transitionRangeX - focusCenterX) / transitionRangeX;
                screenOffsetX = (int) (transition.currScreenOffsetX - normalizedFocusPosition * transition.offsetChangeX);
            }
            else {
                inTransitionX = false;
            }
        }
        else {
            inTransitionX = false;
        }
    }

    public void transitionY() {

        int screenH = Main.WINDOW.SCREEN.screenHeight();
        int focusCenterY = focus.top();

        if (screenOffsetY > transition.nextScreenOffsetY) {

            int currMapSectionBottomY = (drawnMap.mapSectionNumber[0] + 1) * screenH;
            int focusDistanceToMapSectionBottomY = currMapSectionBottomY - focusCenterY;

            //If still within transition bounds
            if (focusDistanceToMapSectionBottomY <= transitionRangeY && focusDistanceToMapSectionBottomY >= 0) {
                double normalizedFocusPosition = 1.0 * (focusCenterY - (currMapSectionBottomY - transitionRangeY)) / transitionRangeY;
                screenOffsetY = (int) (transition.currScreenOffsetY - normalizedFocusPosition * transition.offsetChangeY);
            }
            else {
                inTransitionY = false;
            }
        }

        else if (screenOffsetY < transition.nextScreenOffsetY) {
            int currMapSectionTopY = drawnMap.mapSectionNumber[0] * screenH;
            int focusDistanceToMapSectionTopY = focusCenterY - currMapSectionTopY;

            //If within transition bounds
            if (focusDistanceToMapSectionTopY <= transitionRangeY && focusDistanceToMapSectionTopY >= 0) {

                //Look above for comments
                double normalizedFocusPosition = 1.0 * (currMapSectionTopY + transitionRangeY - focusCenterY) / transitionRangeY;
                screenOffsetY = (int) (transition.currScreenOffsetY - normalizedFocusPosition * transition.offsetChangeY);
            }
            else {
                inTransitionY = false;
            }
        }
        else {
            inTransitionY = false;
        }
    }

    public boolean inFieldView(Area area) {
        int mapLeft = drawnMap.drawColumnStart * Tile.TILE_WIDTH;
        int mapRight = (drawnMap.drawColumnEnd + 1) * Tile.TILE_WIDTH;
        int mapTop = drawnMap.drawRowStart * Tile.TILE_WIDTH;
        int mapBottom = (drawnMap.drawRowEnd + 1) * Tile.TILE_WIDTH;

        boolean left = area.getX() >= mapLeft && area.getX() <= mapRight;
        boolean right = area.getX() + area.getW() >= mapLeft && area.getX() + area.getW() <= mapRight;
        boolean top = area.getY() >= mapTop && area.getY() <= mapBottom;
        boolean bottom = area.getY() + area.getH() >= mapTop && area.getY() + area.getH() <= mapBottom;

        return left || right && top || bottom;
    }

    static class Focus {
        //This is just an entity with like encapsulated methods to help prevent errors on my part and to make it more readable
        Entity e;

        Focus(Entity entity) {
            e = entity;
        }

        int middleX() {
            return e.getX() + e.getW() / 2;
        }
        int top() {
            return e.top();
        }

        boolean movingLeft() {
            return e.directionX() < 0;
        }
        boolean movingRight() {
            return e.directionX() > 0;
        }
        boolean movingUp() {
            return e.directionY() < 0 || e.jumping();
        }
        boolean movingDown() {
            return e.directionY() > 0 || e.gravity() > 0;
        }
    }

    static class DrawnMap {

        //The map is divided up into sections. What determines the section is the screen width and height.
        //For example, if the map width was 10px and the screen width was 5px,
        //there would be 2 sections with the first section being 0 and the second being 1.
        int[] mapSectionNumber = {0, 0};

        //Maximum number of rows/columns that can be shown on one screen
        int screenRows = 0;
        int screenColumns = 0;

        //Start drawing rows from this row start and stop drawing rows at this row end
        int drawRowStart = 0;
        int drawRowEnd = screenRows;

        //Start drawing columns from this column start and stop drawing columns at this column end
        int drawColumnStart = 0;
        int drawColumnEnd = screenColumns;

        void setScreenRows(int n) {
            screenRows = n;

            //Initializing
            drawRowStart = mapSectionNumber[0] * screenRows;
            drawRowEnd = drawRowStart + screenRows;
        }

        void setScreenColumns(int n) {
            screenColumns = n;

            //Initializing
            drawColumnStart = mapSectionNumber[1] * screenColumns;
            drawColumnEnd = drawColumnStart + screenColumns;
        }

        void setMapSectionY(int n) {
            mapSectionNumber[0] = n;
            drawRowStart = mapSectionNumber[0] * screenRows;
            drawRowEnd = drawRowStart + screenRows;
        }

        void setMapSectionX(int n) {
            mapSectionNumber[1] = n;
            drawColumnStart = mapSectionNumber[1] * screenColumns;
            drawColumnEnd = drawColumnStart + screenColumns;
        }

        //Expanding is used for more fluid transitions between map sections. A negative number means to expand the map on its left end,
        //and a positive number means to expand the map on its right end

        void expandRows(int n) {
            if (n == -1) drawRowStart = Math.max(0, drawRowStart - screenRows);
            else if (n == 1) drawRowEnd += screenRows;
        }

        void expandColumns(int n) {
            if (n == -1) drawColumnStart = Math.max(0, drawColumnStart - screenColumns);
            else if (n == 1) drawColumnEnd += screenColumns;
        }
    }

    static class Transition {

        //currScreenOffset - The screen offset at the start of transition, this is what we transition from
        //nextScreenOffset - The screen offset at the end of transition, this is what we transition to
        //offsetChange - This is the total change in screen offset from the current to next

        int currScreenOffsetX;
        int nextScreenOffsetX;
        int offsetChangeX;

        int currScreenOffsetY;
        int nextScreenOffsetY;
        int offsetChangeY;

        public void markTransitionOffsetsX(int c, int n) {
            currScreenOffsetX = c;
            nextScreenOffsetX = n;
            offsetChangeX = Math.abs(n) - Math.abs(currScreenOffsetX);
        }

        public void markTransitionOffsetsY(int c, int n) {
            currScreenOffsetY = c;
            nextScreenOffsetY = n;
            offsetChangeY = Math.abs(n) - Math.abs(currScreenOffsetY);
        }
    }

}
