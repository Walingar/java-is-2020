package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ColorConverter implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int length = image.length;
        int width = image[0].length;
        var colorImage = new Color[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                colorImage[i][j] = new Color(image[i][j]);
            }
        }

        return colorImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int length = image.length;
        int width = image[0].length;
        var intImage = new int[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                intImage[i][j] = image[i][j].getRGB();
            }
        }

        return intImage;
    }
}
