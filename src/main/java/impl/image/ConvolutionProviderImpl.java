package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;
import java.util.function.ToIntFunction;

public final class ConvolutionProviderImpl implements ConvolutionProvider {
    private static final int FILTERED_ALPHA_CHANNEL = 255;

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] filteredImage = new Color[image.length][];
        for (int x = 0; x < image.length; x++) {
            Color[] filteredRow = new Color[image[x].length];
            for (int y = 0; y < image[x].length; y++) {
                filteredRow[y] = apply(x, y, image, kernel);
            }
            filteredImage[x] = filteredRow;
        }
        return filteredImage;
    }

    private Color apply(int x, int y, Color[][] image, double[][] kernel) {
        int filteredRed = apply(x, y, image, Color::getRed, kernel);
        int filteredGreen = apply(x, y, image, Color::getGreen, kernel);
        int filteredBlue = apply(x, y, image, Color::getBlue, kernel);
        return new Color(filteredRed, filteredGreen, filteredBlue, FILTERED_ALPHA_CHANNEL);
    }

    private int apply(int x, int y, Color[][] image, ToIntFunction<Color> channelExtractor, double[][] kernel) {
        int filteredChannel = 0;
        int a = kernel.length / 2;
        int b = kernel.length / 2;
        for (int s = -a; s <= a; s++) {
            for (int t = -b; t <= b; t++) {
                if (x - s < 0 || x - s >= image.length || y - t < 0 || y - t >= image[x - s].length) {
                    continue;
                }
                filteredChannel += kernel[s + a][t + a] * channelExtractor.applyAsInt(image[x - s][y - t]);
            }
        }
        return filteredChannel;
    }
}
