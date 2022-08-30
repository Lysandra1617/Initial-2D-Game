package Utilities;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UT_File {
    //If a file exists
    public static boolean existingFile(String filepath) {
        File file = new File(filepath);
        return file.isFile();
    }

    //Returns an array of Strings that represent the files in a directory
    public static String[] directoryFiles(String directory) {
        File dir = new File(directory);
        if (dir.isDirectory()) return dir.list();
        return null;
    }

    //Deletes a file
    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.delete()) System.out.println("File ("+filepath+") Deleted");
        else System.out.println("File ("+filepath+") Not Deleted");
    }

    //Renames a file
    public static void renameFile(String filepath, String newPath) {
        File oldF = new File(filepath);
        File newF = new File(newPath);

        if (newF.exists()) {
            System.out.println("File ("+filepath+") Already Exists");
        }
        else {
            if (oldF.renameTo(newF)) {
                System.out.println("File ("+filepath+") Renamed");
            }
            else {
                System.out.println("File ("+filepath+") Not Renamed");
            }
        }
    }

    //What is the name of this method
    public static BufferedImage getBufferedImage(String filepath) {
        BufferedImage img;
        File file = new File(filepath);
        try {
            img = ImageIO.read(file);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return img;
    }

    public static Font getFont(String filepath) {
        Font font;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = Font.createFont(Font.TRUETYPE_FONT, new File(filepath));
            ge.registerFont(font);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return font;
    }

    //Assuming that there are no spaces between the rows and columns, this will get the parts of an image
    public static BufferedImage[] extractImages(BufferedImage parentImage, int rows, int columns) {
        BufferedImage[] subImages = new BufferedImage[rows * columns];
        final int SUB_IMG_WIDTH = parentImage.getWidth() / columns;
        final int SUB_IMG_HEIGHT = parentImage.getHeight() / rows;

        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                subImages[i] = parentImage.getSubimage(c * SUB_IMG_WIDTH, r * SUB_IMG_HEIGHT, SUB_IMG_WIDTH, SUB_IMG_HEIGHT);
                i++;
            }
        }
        return subImages;
    }
}
