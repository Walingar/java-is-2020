package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        var height = image.length;
        if (height == 0) {
            return new Color[0][];
        }
        var width = image[0].length;
        var colorMatrix = new Color[height][width];
        for (var row = 0; row < height; row++) {
            for (var column = 0; column < width; column++) {
                colorMatrix[row][column] = new Color(image[row][column], true);
            }
        }
        return colorMatrix;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        var height = image.length;
        if ( height == 0 ) {
            return new int[0][];
        }
        var width = image[0].length;
        var intMatrix = new int[height][width];
        for (var row = 0; row < height; row++) {
            for (var column = 0; column < width; column++) {
                intMatrix[row][column] = image[row][column].getRGB();
            }
        }
        return intMatrix;
    }
}
