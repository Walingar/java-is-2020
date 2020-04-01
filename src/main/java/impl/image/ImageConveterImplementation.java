package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConveterImplementation implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] imageColor = new Color[image.length][image[0].length];
        int colorLength = 32;
        int alphaRightBit = 8;
        int RedRightBit = 16;
        int GreenRightBit = 24;
        int base = 2;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                imageColor[i][j] = new Color(image[i][j]);
            }
        }
        return imageColor;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] imageInt = new int[image.length][image[0].length];
        int colorLength = 8;
        int base = 2;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                imageInt[i][j] = image[i][j].getRGB();
            }
        }
        return imageInt;
    }
}
