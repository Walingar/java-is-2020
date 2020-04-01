package api.image;

import java.awt.*;

public interface ImageConverter {
    Color[][] convertToColor(int[][] image);

    int[][] convertToRgb(Color[][] image);
}