package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterFactory {
    public static ImageConverter getInstance() {
        ImageConverter converter = new ImageConverter() {
            @Override
            public Color[][] convertToColor(int[][] image) {
                Color[][] picture = new Color[image.length][image[0].length];
                for (int row = 0; row<image.length; row++){
                    for (int column = 0; column<image[row].length; column++){
                        picture[row][column] = new Color(image[row][column]);
                    }
                }
                return picture;
            }

            @Override
            public int[][] convertToRgb(Color[][] image) {
                int [][] picture = new int[image.length][image[0].length];
                for (int row = 0; row<image.length; row++){
                    for (int column = 0; column<image[row].length; column++){
                        picture[row][column] = image[row][column].getRGB();
                    }
                }
                return picture;
            }
        };
        return converter;
    }
}