package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] imageCopy = new Color[image.length][];
        for (var i = 0; i < image.length; i++){
            imageCopy[i] = new Color[image[i].length];
        }

        for (var row = 0; row < image.length; row++) {
            for (var column = 0; column < image[row].length; column++) {
                imageCopy[row][column] = multiply(image, kernel, row, column);
            }
        }
        return imageCopy;
    }

    private Color multiply(Color[][] image, double[][] kernel, int row, int column) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        for (var i = 0; i < kernel.length; i++) {
            for (var j = 0; j < kernel[i].length; j++) {
                var radiusRow = (kernel.length - 1) / 2;
                var radiusColumn = (kernel[i].length - 1) / 2;
                var currentRow = row + i - radiusRow;
                var currentColumn = column + j - radiusColumn;
                if (areValidIndexes(currentRow, currentColumn, image)) {
                    var currentColor = image[currentRow][currentColumn];
                    var currentKernelValue = kernel[i][j];
                    sumRed += currentColor.getRed() * currentKernelValue;
                    sumGreen += currentColor.getGreen() * currentKernelValue;
                    sumBlue += currentColor.getBlue() * currentKernelValue;
                }
            }
        }

        return new Color(getColorFromSum(sumRed), getColorFromSum(sumGreen), getColorFromSum(sumBlue));
    }

    private boolean areValidIndexes(int row, int column, Color[][] image) {
        return  row >= 0 && row < image.length && column >= 0 && column < image[row].length;
    }

    private int getColorFromSum(int color) {
        return Math.min(Math.max(0, color), 255);
    }
}
