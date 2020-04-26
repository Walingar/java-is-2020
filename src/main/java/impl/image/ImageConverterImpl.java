package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int rowsLength = image.length;
        int columnsLength = image[0].length;
        Color[][] colors = new Color[rowsLength][columnsLength];
        for (int row = 0; row < rowsLength; row++) {
            for (int column = 0; column < columnsLength; column++) {
                colors[row][column] = new Color(image[row][column]);
            }
        }
        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int rowsLength = image.length;
        int columnsLength = image[0].length;
        int[][] pixelColor = new int[rowsLength][columnsLength];
        for (int row = 0; row < rowsLength; row++) {
            for (int column = 0; column < columnsLength; column++) {
                pixelColor[row][column] = image[row][column].getRGB();
            }
        }
        return pixelColor;
    }
}
