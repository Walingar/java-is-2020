package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;

        Color[][] colorMatrix = new Color[imageHeight][imageWidth];

        for (int imageRowNumber = 0; imageRowNumber < imageHeight; imageRowNumber++) {
            for (int imageColumnNumber = 0; imageColumnNumber < imageWidth; imageColumnNumber++) {
                int rgbColor = image[imageRowNumber][imageColumnNumber];
                colorMatrix[imageRowNumber][imageColumnNumber] = new Color(rgbColor);
            }
        }

        return colorMatrix;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;

        int[][] imageMatrix = new int[imageHeight][imageWidth];

        for (int imageRowNumber = 0; imageRowNumber < imageHeight; imageRowNumber++) {
            for (int imageColumnNumber = 0; imageColumnNumber < imageWidth; imageColumnNumber++) {
                Color color = image[imageRowNumber][imageColumnNumber];
                imageMatrix[imageRowNumber][imageColumnNumber] = color.getRGB();
            }
        }

        return imageMatrix;
    }
}
