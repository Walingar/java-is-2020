package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;

        Color[][] outputColorImage = new Color[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                outputColorImage[i][j] = new Color(image[i][j]);
            }
        }

        return outputColorImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;

        int[][] outputIntImage = new int[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                outputIntImage[i][j] = image[i][j].getRGB();
            }
        }

        return outputIntImage;
    }
}
