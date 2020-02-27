package api.image.impl;

import api.image.ImageConverter;

import java.awt.*;


public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        Color[][] convertedImage = new Color[rows][cols];

        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                convertedImage[i][j] = new Color(image[i][j], true);
            }
        }

        return convertedImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        int[][] convertedImage = new int[rows][cols];

        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                convertedImage[i][j] = image[i][j].getRGB();
            }
        }

        return convertedImage;
    }
}
