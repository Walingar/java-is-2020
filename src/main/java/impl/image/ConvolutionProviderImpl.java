package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    public Color[][] apply(Color[][] image, double[][] kernel) {
        int rowNum = image.length;
        int colNum = image[0].length;
        Color[][] filteredImage = new Color[rowNum][colNum];
        for (int row = 0; row < rowNum; row++) {
            for (int column = 0; column < colNum; column++) {
                filteredImage[row][column] = ConvolutionProviderImpl.applyToPixel(image, kernel, row, column);
            }
        }
        return filteredImage;
    }

    private static Color applyToPixel(Color[][] image, double[][] kernel, int imageRow, int imageColumn) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int kernelRadius = kernel.length / 2;
        for (int rowShift = -kernelRadius; rowShift <= kernelRadius; rowShift++) {
            for (int columnShift = -kernelRadius; columnShift <= kernelRadius; columnShift++) {
                int rowIndex = imageRow + rowShift;
                int columnIndex = imageColumn + columnShift;

                if (rowIndex >= 0 && rowIndex < image.length && columnIndex >= 0 && columnIndex < image[0].length) {
                    Color imageColor = image[rowIndex][columnIndex];
                    double kernelValue = kernel[rowShift + kernelRadius][columnShift + kernelRadius];
                    red += imageColor.getRed() * kernelValue;
                    green += imageColor.getGreen() * kernelValue;
                    blue += imageColor.getBlue() * kernelValue;
                }
            }
        }

        return new Color(red, green, blue);
    }

}
