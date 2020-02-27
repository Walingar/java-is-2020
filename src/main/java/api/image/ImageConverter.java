package api.image;

import java.awt.Color;

public interface ImageConverter {

  Color[][] convertToColor(int[][] image);

  int[][] convertToRgb(Color[][] image);
}