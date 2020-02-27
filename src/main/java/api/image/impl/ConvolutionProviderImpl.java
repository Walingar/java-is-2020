package api.image.impl;

import api.image.ConvolutionProvider;
import java.awt.Color;

public class ConvolutionProviderImpl implements ConvolutionProvider {

  @Override
  public Color[][] apply(Color[][] image, double[][] kernel) {
    int rows = image.length;
    int columns = image[0].length;
    Color[][] convolutionImage = new Color[rows][columns];

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        convolutionImage[row][column] = performConvolution(image, kernel, row, column);
      }
    }

    return convolutionImage;
  }

  private Color performConvolution(Color[][] image, double[][] kernel, int row, int column) {
    int sumRed = 0;
    int sumGreen = 0;
    int sumBlue = 0;

    for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
      for (var kernelColumn = 0; kernelColumn < kernel[kernelRow].length; kernelColumn++) {
        double currentKernelValue = kernel[kernelRow][kernelColumn];
        int currentRow = row + kernelRow - (kernel.length - 1) / 2;
        int currentColumn = column + kernelColumn - (kernel[kernelRow].length - 1) / 2;

        if (areNotValidIndexes(image, currentRow, currentColumn)) {
          continue;
        }

        Color currentColor = image[currentRow][currentColumn];
        sumRed += currentColor.getRed() * currentKernelValue;
        sumGreen += currentColor.getGreen() * currentKernelValue;
        sumBlue += currentColor.getBlue() * currentKernelValue;
      }
    }

    return new Color(gerColorValue(sumRed), gerColorValue(sumGreen), gerColorValue(sumBlue));
  }

  private boolean areNotValidIndexes(Color[][] image, int row, int column) {
    return !(row >= 0 && row < image.length && column >= 0 && column < image[row].length);
  }

  private int gerColorValue(int color) {
    return Math.min(Math.max(0, color), 255);
  }
}
