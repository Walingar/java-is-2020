package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterFactory {
    public static ImageConverter getInstance() {

        ImageConverter imageConverter = new ImageConverter() {
            @Override
            public Color[][] convertToColor(int[][] image) {
                Color[][] colors  = new Color[image.length][image[0].length];
                for (int row = 0; row < image.length; row++) {
                    for (int column = 0; column<image[0].length; column++){
                        colors[row][column] = new Color(image[row][column]);
                    }
                }
                return colors;
            }

            @Override
            public int[][] convertToRgb(Color[][] image) {
                int[][] pixelColor = new int[image.length][image[0].length];
                for (int row =0; row<image.length; row++){
                    for (int column = 0; column<image[row].length; column++){
                        pixelColor[row][column] = image[row][column].getRGB();
                    }
                }
                return pixelColor;
            }
        };
        return imageConverter;
    }
}