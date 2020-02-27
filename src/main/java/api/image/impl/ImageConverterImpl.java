package api.image.impl;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        if (image.length == 0) {
            return new Color[0][];
        }
        int height = image.length;
        int width = image[0].length;
        Color[][] colors = new Color[height][width];
        for (int xId = 0; xId < height; xId++) {
            for (int yId = 0; yId < width; yId++) {
                colors[xId][yId] = new Color(image[xId][yId], true);
            }
        }
        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        if (image.length == 0) {
            return new int[0][];
        }
        int height = image.length;
        int width = image[0].length;
        int[][] ints = new int[height][width];
        for (int xId = 0; xId < height; xId++) {
            for (int yId = 0; yId < width; yId++) {
                ints[xId][yId] = image[xId][yId].getRGB();
            }
        }
        return ints;
    }
}
