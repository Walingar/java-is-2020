package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public final class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int height = image.length;
        Color[][] colors = new Color[height][];
        for (int i = 0; i < height; i++) {
            int width = image[i].length;
            Color[] row = new Color[width];
            for (int j = 0; j < width; j++) {
                row[j] = new Color(image[i][j], true);
            }
            colors[i] = row;
        }
        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int height = image.length;
        int[][] rgbs = new int[height][];
        for (int i = 0; i < height; i++) {
            int width = image[i].length;
            int[] row = new int[width];
            for (int j = 0; j < width; j++) {
                row[j] = image[i][j].getRGB();
            }
            rgbs[i] = row;
        }
        return rgbs;
    }
}
