package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] colors = new Color[image.length][];
        for (int rowIndex = 0; rowIndex < image.length; rowIndex++) {
            int[] row = image[rowIndex];
            colors[rowIndex] = new Color[row.length];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                colors[rowIndex][columnIndex] = new Color(row[columnIndex]);
            }
        }
        return colors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] rgbs = new int[image.length][];
        for (int rowIndex = 0; rowIndex < image.length; rowIndex++) {
            Color[] row = image[rowIndex];
            rgbs[rowIndex] = new int[row.length];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                rgbs[rowIndex][columnIndex] = row[columnIndex].getRGB();
            }
        }
        return rgbs;
    }
}
