package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    public Color[][] convertToColor(int[][] image) {
        Color[][] color = new Color[image.length][];

        for (int i = 0; i < image.length; i++) {
            color[i] = new Color[image[i].length];

            for (int j = 0; j < image[i].length; j++) {
                color[i][j] = new Color(image[i][j]);
            }
        }

        return color;
    }

    public int[][] convertToRgb(Color[][] image) {
        int[][] rgb = new int[image.length][];

        for (int i = 0; i < image.length; i++) {
            rgb[i] = new int[image[i].length];

            for (int j = 0; j < image[i].length; j++) {
                rgb[i][j] = image[i][j].getRGB();
            }
        }

        return rgb;
    }
}