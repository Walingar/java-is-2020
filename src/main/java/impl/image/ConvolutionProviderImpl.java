package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] convolution = new Color[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                convolution[i][j] = multiply(image, kernel, i, j);
            }
        }
        return convolution;
    }

    private Color multiply(Color[][] image, double[][] kernel, int row, int col) {
        int red = 0;
        int green = 0;
        int blue = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                int radiusRow = (kernel.length - 1) / 2;
                int radiusCol = (kernel[i].length - 1) / 2;
                int currentRow = row + i - radiusRow;
                int currentCol = col + j - radiusCol;
                if (currentRow >= 0 && currentCol >= 0 && currentRow < image.length && currentCol < image[row].length) {
                    Color currentColor = image[currentRow][currentCol];
                    double currentKernel = kernel[i][j];
                    red += currentColor.getRed() * currentKernel;
                    green += currentColor.getGreen() * currentKernel;
                    blue += currentColor.getBlue() * currentKernel;
                }
            }
        }
        return new Color(Math.max(0, Math.min(red, 255)), Math.max(0, Math.min(green, 255)), Math.max(0, Math.min(blue, 255)));
    }
}
