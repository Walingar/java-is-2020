package impl.image;

import java.awt.*;

public class ImageConverterFactory {
    public static ImageConverterImpl getInstance() {
        return new ImageConverterImpl();
    }

    public static class ImageConverterImpl implements api.image.ImageConverter {
        @Override
        public Color[][] convertToColor(int[][] image) {
            int imgHeight = image.length;
            int imgWidth = image[0].length;
            Color[][] resultColor = new Color[imgHeight][imgWidth];
            for (int i = 0; i < imgHeight; i++) {
                for (int j = 0; j < imgWidth; j++) {
                    resultColor[i][j] = new Color(image[i][j]);
                }
            }
            return resultColor;
        }

        @Override
        public int[][] convertToRgb(Color[][] image) {
            int imgHeight = image.length;
            int imgWidth = image[0].length;
            int[][] resultRGB = new int[imgHeight][imgWidth];
            for (int i = 0; i < imgHeight; i++) {
                for (int j = 0; j < imgWidth; j++) {
                    resultRGB[i][j] = image[i][j].getRGB();
                }
            }
            return resultRGB;
        }
    }
}