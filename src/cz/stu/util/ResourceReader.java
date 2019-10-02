package cz.stu.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceReader {
    public static BufferedImage loadImage(String filename) {
        try {
            ClassLoader classLoader = ResourceReader.class.getClassLoader();
            File file = new File(classLoader.getResource(filename).getFile());
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Font loadFont(String filename) {
        try {
            ClassLoader classLoader = ResourceReader.class.getClassLoader();
            File file = new File(classLoader.getResource(filename).getFile());
            return Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
