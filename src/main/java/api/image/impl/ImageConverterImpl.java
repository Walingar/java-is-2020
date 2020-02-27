package api.image.impl;

import api.image.ImageConverter;
import java.awt.Color;

public class ImageConverterImpl implements ImageConverter {

  @Override
  public Color[][] convertToColor(int[][] image) {
    int rows = image.length;
    int columns = image[0].length;
    Color[][] convertedImage = new Color[rows][columns];

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        convertedImage[row][column] = new Color(image[row][column], true);
      }
    }

    return convertedImage;
  }

  @Override
  public int[][] convertToRgb(Color[][] image) {
    int rows = image.length;
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
