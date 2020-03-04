package impl.image;

import api.image.ImageConverter;
import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] colorImage = new Color[image.length][];
        for (int row = 0; row < image.length; row++) {
            var imageRow = image[row];
            colorImage[row] = new Color[imageRow.length];
            for (int column = 0; column < imageRow.length; column++) {
                colorImage[row][column] = new Color(imageRow[column]);
            }
        }
        return colorImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] rgbImage = new int[image.length][];
        for (int row = 0; row < image.length; row++) {
            var imageRow = image[row];
            rgbImage[row] = new int[image[row].length];
            for (int column = 0; column < imageRow.length; column++) {
                rgbImage[row][column] = imageRow[column].getRGB();
            }
        }
        return rgbImage;
    }
}