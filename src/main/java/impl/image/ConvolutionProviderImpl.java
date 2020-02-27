package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public final class ConvolutionProviderImpl implements ConvolutionProvider {
    private static final int FILTERED_ALPHA_CHANNEL = 255;

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int height = image.length;
        Color[][] filteredImage = new Color[height][];
        for (int x = 0; x < height; x++) {
            int width = image[x].length;
            Color[] filteredRow = new Color[width];
            for (int y = 0; y < width; y++) {
                filteredRow[y] = apply(x, y, image, kernel);
            }
            filteredImage[x] = filteredRow;
        }
        return filteredImage;
    }

    private Color apply(int imageRow, int imageCol, Color[][] image, double[][] kernel) {
        int filteredRed = 0;
        int filteredGreen = 0;
        int filteredBlue = 0;
        for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
            int halfKernelHeight = kernel.length / 2;
            int appliedRow = imageRow + kernelRow - halfKernelHeight;
            for (int kernelCol = 0; kernelCol < kernel[kernelRow].length; kernelCol++) {
                int halfKernelWidth = kernel[kernelRow].length / 2;
                int appliedCol = imageCol + kernelCol - halfKernelWidth;
                if (appliedRow < 0 || appliedRow >= image.length
                        || appliedCol < 0 || appliedCol >= image[appliedRow].length) {
                    continue;
                }
                filteredRed += kernel[kernelRow][kernelCol] * image[appliedRow][appliedCol].getRed();
                filteredGreen += kernel[kernelRow][kernelCol] * image[appliedRow][appliedCol].getGreen();
                filteredBlue += kernel[kernelRow][kernelCol] * image[appliedRow][appliedCol].getBlue();
            }
        }
        return new Color(filteredRed, filteredGreen, filteredBlue, FILTERED_ALPHA_CHANNEL);
    }
}
