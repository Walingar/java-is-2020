package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        Color[][] converted = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                converted[i][j] = new Color(image[i][j]);
            }
        }
        return converted;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        int[][] converted = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                converted[i][j] = image[i][j].getRGB();
            }
        }
        return converted;
    }
}
