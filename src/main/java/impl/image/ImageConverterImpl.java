package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int height = image.length;
        Color[][] imageColor = new Color[height][];
        for (int rowNumber = 0; rowNumber < height; rowNumber++) {
            int[] row = image[rowNumber];
            int rowLength = row.length;
            imageColor[rowNumber] = new Color[rowLength];
            for (int columnNumber = 0; columnNumber < rowLength; columnNumber++) {
                imageColor[rowNumber][columnNumber] = new Color(row[columnNumber]);
            }
        }
        return imageColor;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int height = image.length;
        int[][] imageRGB = new int[height][];
        for (int rowNumber = 0; rowNumber < height; rowNumber++) {
            Color[] row = image[rowNumber];
            int rowLength = row.length;
            imageRGB[rowNumber] = new int[rowLength];
            for (int columnNumber = 0; columnNumber < rowLength; columnNumber++) {
                imageRGB[rowNumber][columnNumber] = row[columnNumber].getRGB();
            }
        }
        return imageRGB;
    }
}