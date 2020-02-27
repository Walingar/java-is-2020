package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        int imgHeight = image.length;
        int imgWidth = image[0].length;
        Color[][] outputColor = new Color[imgHeight][imgWidth];
        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                outputColor[i][j] = new Color(image[i][j]);
            }
        }
        return outputColor;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int imgHeight = image.length;
        int imgWidth = image[0].length;
        int[][] outputRGB = new int[imgHeight][imgWidth];
        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                outputRGB[i][j] = image[i][j].getRGB();
            }
        }
        return outputRGB;
    }
}
