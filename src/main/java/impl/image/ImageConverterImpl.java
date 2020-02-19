package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] colorImage = new Color[image.length][];
        for (int row = 0; row < image.length; row++) {
            colorImage[row] = new Color[image[row].length];
            for (int column = 0; column < image[row].length; column++) {
                colorImage[row][column] = new Color(image[row][column]);
            }
        }
        return colorImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] rgbImage = new int[image.length][];
        for (int row = 0; row < image.length; row++) {
            rgbImage[row] = new int[image[row].length];
            for (int column = 0; column < image[row].length; column++) {
                rgbImage[row][column] = image[row][column].getRGB();
            }
        }
        return rgbImage;
    }
}
