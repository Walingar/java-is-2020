package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] image_copy = new Color[image.length][image[0].length];

        for (var row = 0; row < image.length; row++) {
            for (var column = 0; column < image[row].length; column++) {
                image_copy[row][column] = multiply(image, kernel, row, column);
            }
        }
        return image_copy;
    }

    private Color multiply(Color[][] image, double[][] kernel, int row, int column) {
        var radius = (kernel.length - 1) / 2;
        int sumRed = 0, sumGreen = 0, sumBlue = 0;

        for (var i = 0; i < kernel.length; i++) {
            for (var j = 0; j < kernel[i].length; j++) {
                var currentRow = row + i - radius;
                var currentColumn = column + j - radius;
                if (areValidIndexes(currentRow, currentColumn, image)) {
                    sumRed += image[currentRow][currentColumn].getRed() * kernel[i][j];
                    sumGreen += image[currentRow][currentColumn].getGreen() * kernel[i][j];
                    sumBlue += image[currentRow][currentColumn].getBlue() * kernel[i][j];
                }
            }
        }

        return new Color(Math.min(sumRed, 255), Math.min(sumGreen, 255), Math.min(sumBlue, 255));
    }

    private boolean areValidIndexes(int row, int column, Color[][] image)
    {
        return  row >= 0 && row < image.length && column >= 0 && column < image[row].length;
    }
}
