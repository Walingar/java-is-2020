package impl.image;

import java.awt.*;

public class ImageConverterImpl implements api.image.ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        int rows = image.length;
        if (rows == 0) {
            return new Color[0][0];
        }
        int cols = image[0].length;

        Color[][] convertedImage = new Color[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                convertedImage[i][j] = new Color(image[i][j]);
            }
        }
        return convertedImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int rows = image.length;
        if (rows == 0) {
            return new int[0][0];
        }
        int cols = image[0].length;

        int[][] convertedImage = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                convertedImage[i][j] = image[i][j].getRGB();
            }
        }
        return convertedImage;
    }
}
