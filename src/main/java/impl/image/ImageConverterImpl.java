package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        int imageLength = image.length;
        Color[][] result = new Color[imageLength][];
        for (int i = 0; i < imageLength; i++) {
            int rowLength = image[i].length;
            result[i] = new Color[rowLength];
            for (int j = 0; j < rowLength; j++) {
                result[i][j] = new Color(image[i][j]);
            }
        }
        return result;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int imageLength = image.length;
        int[][] result = new int[imageLength][];
        for (int i = 0; i < imageLength; i++) {
            int rowLength = image[i].length;
            result[i] = new int[rowLength];
            for (int j = 0; j < rowLength; j++) {
                result[i][j] = image[i][j].getRGB();
            }
        }
        return result;
    }
}
