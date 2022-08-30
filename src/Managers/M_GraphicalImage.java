package Managers;

import Utilities.UT_File;

import java.awt.image.BufferedImage;

public class M_GraphicalImage {
    //Below are all the available images to be used in the GUI
    public final BufferedImage BUTTON_BG;
    public final BufferedImage SHORT_L;
    public final BufferedImage SHORT_U;
    public final BufferedImage SHORT_D;
    public final BufferedImage SHORT_R;
    public final BufferedImage LONG_L;
    public final BufferedImage LONG_R;
    public final BufferedImage PLUS;
    public final BufferedImage TOGGLE_ON;
    public final BufferedImage TOGGLE_OFF;
    public final BufferedImage LETTER_X;
    public final BufferedImage BLK_YEL_BOX;
    public final BufferedImage TRANSP_BOX;
    public final BufferedImage PATCH_BG;

    public M_GraphicalImage() {
        //Loading all the images
        final BufferedImage IMG = UT_File.getBufferedImage("src\\Resources\\GUI\\Buttons.png");
        if (IMG == null) {
            BUTTON_BG = null;
            SHORT_L = null;
            SHORT_U = null;
            SHORT_D = null;
            SHORT_R = null;
            LONG_L = null;
            LONG_R = null;
            PLUS = null;
            TOGGLE_ON = null;
            TOGGLE_OFF = null;
            LETTER_X = null;
            BLK_YEL_BOX = null;
            TRANSP_BOX = null;
            PATCH_BG = null;
            return;
        }
        BUTTON_BG = IMG.getSubimage(0,0,96,48);
        SHORT_L = IMG.getSubimage(0,48,16,16);
        SHORT_R = IMG.getSubimage(16,48,16,16);
        SHORT_U = IMG.getSubimage(32, 64,16,16);
        SHORT_D = IMG.getSubimage(32,48,16,16);
        LONG_L = IMG.getSubimage(0,64,26,16);
        LONG_R = IMG.getSubimage(0,80,32,16);
        PLUS = IMG.getSubimage(48,48,16,16);
        TOGGLE_OFF = IMG.getSubimage(0,96,16,16);
        TOGGLE_ON = IMG.getSubimage(16,96,16,16);
        TRANSP_BOX = IMG.getSubimage(0,112,16,16);
        LETTER_X = IMG.getSubimage(16,112,16,16);
        BLK_YEL_BOX = IMG.getSubimage(32,112,16,16);
        PATCH_BG = IMG.getSubimage(0,128,24,24);
    }
}
