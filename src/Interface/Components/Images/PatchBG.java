package Interface.Components.Images;

import Central.Main;
import Markers.Area;
import Markers.Drawing;
import Utilities.UT_File;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PatchBG extends Area implements Drawing {
    final int PATCH_W = 8; //In the image, each patch is 8x8 pixels
    final int PATCH_H = 8;
    final BufferedImage[] PATCH_BG; //Contains the patches
    Image[][] patches; //Patches put together
    int patchRows, patchColumns; //# of rows and columns of patches needed to cover area
    Area area; //Area being covered

    public PatchBG(Area a) {
        area = a;
        PATCH_BG = UT_File.extractImages(Main.G_IMAGE_MANAGER.PATCH_BG, 3, 3);
        patchwork();
    }

    @Override
    public void setX(int x) {
        this.x = x;
        for (int r = 0; r < patchRows; r++) {
            for (int c = 0; c < patchColumns; c++) {
                patches[r][c].setX(this.x + c * PATCH_W);
            }
        }
    }

    @Override
    public void setY(int y) {
        this.y = y;
        for (int r = 0; r < patchRows; r++) {
            for (int c = 0; c < patchColumns; c++) {
                patches[r][c].setY(this.y + r * PATCH_H);
            }
        }
    }

    @Override
    public void draw(Graphics2D G) {
        for (Image[] row : patches) {
            for (Image img : row) {
                img.draw(G);
            }
        }
    }

    void patchwork() {
        patchColumns = (int) Math.ceil((double) area.getW() / PATCH_W);
        patchRows = (int) Math.ceil((double) area.getH() / PATCH_H);
        patches = new Image[patchRows][patchColumns];
        w = patchColumns * PATCH_W;
        h = patchRows * PATCH_H;

        //Finding which patch to use for each image, setting the image's width and height
        BufferedImage image;
        int rowEnd = patchRows - 1, columnEnd = patchColumns - 1;
        for (int r = 0; r <= rowEnd; r++) {
            for (int c = 0; c <= columnEnd; c++) {
                //Top-left corner
                if (r == 0 && c == 0) image = PATCH_BG[0];
                //Top-right corner
                else if (r == 0 && c == columnEnd) image = PATCH_BG[2];
                //Bottom-left corner
                else if (r == rowEnd && c == 0) image = PATCH_BG[6];
                //Bottom-right corner
                else if (r == rowEnd && c == columnEnd) image = PATCH_BG[8];
                //Top row (excluding corner pieces)
                else if (r == 0) image = PATCH_BG[1];
                //Bottom row (excluding corner pieces)
                else if (r == rowEnd) image = PATCH_BG[7];
                //Left column (excluding corner pieces)
                else if (c == 0) image = PATCH_BG[3];
                //Right column (excluding corner pieces)
                else if (c == columnEnd) image = PATCH_BG[5];
                //Center
                else image = PATCH_BG[4];

                //Initializing the block in that row and column with the image etc
                patches[r][c] = new Image(image);
                patches[r][c].setW(PATCH_W);
                patches[r][c].setH(PATCH_H);
            }
        }
    }
}
