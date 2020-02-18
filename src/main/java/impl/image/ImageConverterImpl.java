package impl.image;

import api.image.ImageConverter;

import java.awt.*;

class ImageConverterImpl implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        var imageHeight = image.length;
        if (imageHeight == 0) {
            return new Color[imageHeight][];
        }
        var imageWidth = image[0].length;
        var convertedImage = new Color[imageHeight][imageWidth];
        for (var row = 0; row < imageHeight; row++) {
            for (var column = 0; column < imageWidth; column++) {
                convertedImage[row][column] = new Color(image[row][column], true);
            }
        }
        return convertedImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        var imageHeight = image.length;
        if (imageHeight == 0) {
            return new int[imageHeight][];
        }
        var imageWidth = image[0].length;
        var convertedImage = new int[imageHeight][imageWidth];
        for (var row = 0; row < imageHeight; row++) {
            for (var column = 0; column < imageWidth; column++) {
                convertedImage[row][column] = image[row][column].getRGB();
            }
        }
        return convertedImage;
    }
}
