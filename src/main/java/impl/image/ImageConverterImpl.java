package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    public Color[][] convertToColor(int[][] image) {
        int n = image.length;
        int m = image[0].length;
        Color[][] img = new Color[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                img[i][j] = new Color(image[i][j]);
            }
        }
        return img;
    }

    public int[][] convertToRgb(Color[][] image) {
        int n = image.length;
        int m = image[0].length;
        int[][] img = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                    img[i][j] = image[i][j].getRGB();
                }
            }
        return img;
    }
}