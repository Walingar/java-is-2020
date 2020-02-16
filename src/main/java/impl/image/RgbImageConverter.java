package impl.image;

import api.image.ImageConverter;
import java.awt.*;

public class RgbImageConverter implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        if (image == null || image.length <= 0)
            return null;

        var rows = image.length;
        var columns = image[0].length;
        var imageColors = new Color[rows][columns];

        for (var i = 0; i < rows; i++)
            for (var j = 0; j < columns; j++)
                imageColors[i][j] = new Color(image[i][j]);

        return imageColors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        if (image == null || image.length <= 0)
            return null;

        var rows = image.length;
        var columns = image[0].length;
        var imageColors = new int[rows][columns];

        for (var i = 0; i < rows; i++)
            for (var j = 0; j < columns; j++)
                imageColors[i][j] = image[i][j].getRGB();

        return imageColors;
    }
}