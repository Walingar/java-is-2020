package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterFactory {
    public static ImageConverter getInstance() {
        return new ImageConverter() {
            @Override
            public Color[][] convertToColor(int[][] image) {
                Color[][] picture = new Color[image.length][image[0].length];

                int imageLength = image.length;
                int imageWidth = image[0].length;

                for (int row = 0; row < imageLength; row++) {
                    for (int column = 0; column < imageWidth; column++) {
                        picture[row][column] = new Color(image[row][column]);
                    }
                }

                return picture;
            }

            @Override
            public int[][] convertToRgb(Color[][] image) {
                int[][] picture = new int[image.length][image[0].length];

                int imageLength = image.length;
                int imageWidth = image[0].length;

                for (int row = 0; row < imageLength; row++) {
                    for (int column = 0; column < imageWidth; column++) {
                        picture[row][column] = image[row][column].getRGB();
                    }
                }

                return picture;
            }
        };
    }
}