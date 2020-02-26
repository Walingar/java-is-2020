package impl.image;

import java.awt.*;

public class ImageConverterFactory {
    public static ImageConverterImpl getInstance() {
        return new ImageConverterImpl();
    }

    public static class ImageConverterImpl implements api.image.ImageConverter {

        private final int green_shift = 8;
        private final int red_shift = 16;
        private final int alpha_shift = 24;

        @Override
        public Color[][] convertToColor(int[][] image) {
            int rows = image.length;
            if (rows == 0) {
                return new Color[0][0];
            }
            int cols = image[0].length;

            Color[][] converted_img = new Color[rows][cols];

            int r, g, b, a;
            int mask = 0xFF;
            for (int i = 0, pixel; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    pixel = image[i][j];
                    r = (pixel & (mask << red_shift)) >>> red_shift;
                    g = (pixel & (mask << green_shift)) >>> green_shift;
                    b = pixel & (mask);
                    a = (pixel & (mask << alpha_shift)) >>> alpha_shift;
                    converted_img[i][j] = new Color(r, g, b, a);
                }
            }
            return converted_img;
        }

        @Override
        public int[][] convertToRgb(Color[][] image) {
            int rows = image.length;
            if (rows == 0) {
                return new int[0][0];
            }
            int cols = image[0].length;

            int[][] converted_img = new int[rows][cols];

            Color color;
            for (int i = 0, pixel; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    color = image[i][j];
                    pixel = 0;
                    pixel |= color.getBlue();
                    pixel |= color.getGreen() << green_shift;
                    pixel |= color.getRed() << red_shift;
                    pixel |= color.getAlpha() << alpha_shift;
                    converted_img[i][j] = pixel;
                }
            }
            return converted_img;
        }
    }
}