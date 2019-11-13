package cz.stu.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> parseLines(String filename) {
        ClassLoader classLoader = ResourceReader.class.getClassLoader();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(new File(classLoader.getResource(filename).getFile()).getAbsolutePath()))) {
            return br.lines().collect(Collectors.toList());
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
