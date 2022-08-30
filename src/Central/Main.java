package Central;

import Managers.M_GraphicalImage;
import Interface.Interface;
import Interface.Window;
import Managers.M_MapImage;

public class Main {
    public static final M_GraphicalImage G_IMAGE_MANAGER = new M_GraphicalImage(); //Images for GUI
    public static final M_MapImage MAP_IMAGE_MANAGER = new M_MapImage(); //Images for map
    final static Game GAME = new Game(); //Game loop
    final public static Window WINDOW = new Window(); //The screen
    final public static Interface INTERFACE = new Interface(); //Handles pages
    
    public static void main(String[] args) {
        GAME.init();
        GAME.start();
    }
}
