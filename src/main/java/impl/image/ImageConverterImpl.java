package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] colors = new Color[image.length][];
        for (int i = 0; i < image.length; i++) {
            colors[i] = new Color[image[i].length];
            for (int j = 0; j < image[i].length; j++) {
                colors[i][j] = new Color(image[i][j]);
            }
        }
        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] rgbs = new int[image.length][];
        for (int i = 0; i < image.length; i++) {
            rgbs[i] = new int[image[i].length];
            for (int j = 0; j < image[i].length; j++) {
                rgbs[i][j] = image[i][j].getRGB();
            }
        }
        return rgbs;
    }
}
