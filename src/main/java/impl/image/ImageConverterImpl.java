package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public final class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        if (image.length == 0) {
            return new Color[][]{};
        }
        int height = image.length;
        int width = image[0].length;
        Color[][] colors = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                colors[i][j] = new Color(image[i][j], true);
            }
        }
        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        if (image.length == 0) {
            return new int[][]{};
        }
        int height = image.length;
        int width = image[0].length;
        int[][] rgbs = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgbs[i][j] = image[i][j].getRGB();
            }
        }
        return rgbs;
    }
}
