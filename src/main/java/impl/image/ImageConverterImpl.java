package impl.image;

import api.image.ImageConverter;
import org.jetbrains.annotations.TestOnly;

import java.awt.*;

public final class ImageConverterImpl implements ImageConverter {
    private static final int BYTE_MASK = 0xff;
    private static final int BLUE_CHANNEL_OFFSET = 0;
    private static final int GREEN_CHANNEL_OFFSET = 8;
    private static final int RED_CHANNEL_OFFSET = 16;
    private static final int ALPHA_CHANNEL_OFFSET = 24;

    @Override
    public Color[][] convertToColor(int[][] image) {
        if (image.length == 0) {
            return new Color[][]{};
        }
        int height = image.length;
        int width = image[0].length;
        Color[][] colors = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                colors[i][j] = convertToColor(image[i][j]);
            }
        }
        return colors;
    }

    @TestOnly
    Color convertToColor(int rgb) {
        int red = (rgb >>> RED_CHANNEL_OFFSET) & BYTE_MASK;
        int green = (rgb >>> GREEN_CHANNEL_OFFSET) & BYTE_MASK;
        int blue = (rgb >>> BLUE_CHANNEL_OFFSET) & BYTE_MASK;
        int alpha = (rgb >>> ALPHA_CHANNEL_OFFSET) & BYTE_MASK;
        return new Color(red, green, blue, alpha);
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        if (image.length == 0) {
            return new int[][]{};
        }
        int height = image.length;
        int width = image[0].length;
        int[][] rgbs = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgbs[i][j] = convertToRgb(image[i][j]);
            }
        }
        return rgbs;
    }

    @TestOnly
    int convertToRgb(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();
        return (red << RED_CHANNEL_OFFSET) +
                (green << GREEN_CHANNEL_OFFSET) +
                (blue << BLUE_CHANNEL_OFFSET) +
                (alpha << ALPHA_CHANNEL_OFFSET);
    }
}
