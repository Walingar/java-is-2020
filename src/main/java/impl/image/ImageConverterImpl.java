package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterImpl implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] imageColors = new Color[image.length][];
        for (int row = 0; row < image.length; row++) {
            int[] buff = image[row];
            imageColors[row] = new Color[buff.length];
            for (int column = 0; column < buff.length; column++) {
                imageColors[row][column] = new Color(buff[column]);
            }
        }
        return imageColors;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] imageRGB = new int[image.length][];
        for (int row = 0; row < image.length; row++) {
            Color[] buff = image[row];
            imageRGB[row] = new int[buff.length];
            for (int column = 0; column < buff.length; column++) {
                imageRGB[row][column] = buff[column].getRGB();
            }
        }
        return imageRGB;
    }
}
