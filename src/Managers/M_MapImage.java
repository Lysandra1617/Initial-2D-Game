package Managers;

import Utilities.UT_File;

import java.awt.image.BufferedImage;

public class M_MapImage {
    //An array of BufferedImage's. The image is found via its position in the loaded PNG.
    //The loaded image has 4x4 squares with different colors. If you wanted the first square, you would access the 0th index.
    public final BufferedImage[] TILE_IMAGES;
    public final BufferedImage[] ITEM_IMAGES;

    public M_MapImage() {
        //Tile Images
        BufferedImage image = UT_File.getBufferedImage("src\\Resources\\Map\\Tilesheet.png");
        if (image == null) TILE_IMAGES = null;
        else TILE_IMAGES = UT_File.extractImages(image, 4, 4);

        //Item Images
        image = UT_File.getBufferedImage("src\\Resources\\Map\\Itemsheet.png");
        if (image == null) ITEM_IMAGES = null;
        else ITEM_IMAGES = UT_File.extractImages(image, 4, 4);
    }

}
