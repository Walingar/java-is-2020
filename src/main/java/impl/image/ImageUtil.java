package impl.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class ImageUtil {
    public static Color[][] readImage(File imageFile) {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file shouldn't be null");
        }
        if (imageFile.exists()) {
            try {
                BufferedImage imageRaw = ImageIO.read(imageFile);
                int[][] image = new int[imageRaw.getHeight()][imageRaw.getWidth()];
                for (int y = 0; y < imageRaw.getHeight(); y++) {
                    for (int x = 0; x < imageRaw.getWidth(); x++) {
                        image[y][x] = imageRaw.getRGB(x, y);
                    }
                }
                return ImageConverterFactory.getInstance().convertToColor(image);
            } catch (IOException e) {
                throw new IllegalStateException("Couldn't read image: " + imageFile.getAbsolutePath(), e);
            }
        } else {
            throw new IllegalStateException(String.format("Image %s doesn't exist", imageFile.getAbsolutePath()));
        }
    }

    public static Color[][] readOriginImage(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Image name shouldn't be null");
        }
        return readImage(new File("resources/image/origin/" + name));
    }

    public static void writeImage(File imageFile, Color[][] image) {
        if (imageFile == null || image == null) {
            throw new IllegalArgumentException("Image file and image shouldn't be null");
        }
        if (image.length == 0 || image[0].length == 0) {
            throw new IllegalArgumentException("Image side shouldn't be 0");
        }
        File parent = imageFile.getParentFile();
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IllegalStateException("Couldn't create folder: " + parent.getAbsolutePath());
            }
        }
        try {
            if (!imageFile.exists()) {
                if (!imageFile.createNewFile()) {
                    throw new IOException();
                }
            }
            int[][] imageRgb = ImageConverterFactory.getInstance().convertToRgb(image);
            BufferedImage imageRaw = new BufferedImage(image[0].length, image.length, TYPE_INT_ARGB);
            for (int y = 0; y < imageRaw.getHeight(); y++) {
                for (int x = 0; x < imageRaw.getWidth(); x++) {
                    imageRaw.setRGB(x, y, imageRgb[y][x]);
                }
            }
            ImageIO.write(imageRaw, "png", imageFile);
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create image: " + imageFile.getAbsolutePath(), e);
        }
    }

    public static void writeOutputImage(String name, Color[][] image) {
        if (name == null || image == null) {
            throw new IllegalArgumentException("Image name and image shouldn't be null");
        }
        writeImage(new File("resources/image/output/" + name), image);
    }
}