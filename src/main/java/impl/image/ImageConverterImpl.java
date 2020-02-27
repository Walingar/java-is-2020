package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int numRows = image.length;
        int numColumns = image[0].length;

        for (int rowIdx = 0; rowIdx < image.length; rowIdx++) {
            if (image[rowIdx].length != numColumns) {
                throw new IllegalArgumentException("Incorrect image format.");
            }
        }

        var colors = new Color[numRows][numColumns];
        for (int rowIdx = 0; rowIdx < numRows; rowIdx++) {
            for (int colIdx = 0; colIdx < numColumns; colIdx++) {
                colors[rowIdx][colIdx] = new Color(image[rowIdx][colIdx], true);
            }
        }

        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int numRows = image.length;
        int numColumns = image[0].length;

        for (int rowIdx = 0; rowIdx < image.length; rowIdx++) {
            if (image[rowIdx].length != numColumns) {
                throw new IllegalArgumentException("Incorrect image format.");
            }
        }

        var colors = new int[image.length][image[0].length];
        for (int rowIdx = 0; rowIdx < numRows; rowIdx++) {
            for (int colIdx = 0; colIdx < numColumns; colIdx++) {
                colors[rowIdx][colIdx] = image[rowIdx][colIdx].getRGB();
            }
        }

        return colors;
    }
}
