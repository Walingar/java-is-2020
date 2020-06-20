package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int imageHeight = image.length;
        if (imageHeight == 0) {
            return new Color[0][0];
        }
        int imageWidth = image[0].length;
        int kernelHeight = kernel.length / 2;
        int kernelWidth = 0;
        if (kernelHeight > 0) {
            kernelWidth = kernel[0].length / 2;
        }
        Color[][] resultImage = new Color[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int kernelRow = -kernelHeight; kernelRow <= kernelHeight; kernelRow++) {
                    for (int kernelCol = -kernelWidth; kernelCol <= kernelWidth; kernelCol++) {
                        int row = i + kernelRow;
                        int col = j + kernelCol;
                        if (row >= 0 && col >= 0 && row < imageHeight && col < imageWidth) {
                            Color elem = image[row][col];
                            double kernelElem = kernel[kernelRow + kernelHeight][kernelCol + kernelWidth];
                            red += elem.getRed() * kernelElem;
                            green += elem.getGreen() * kernelElem;
                            blue += elem.getBlue() * kernelElem;
                        }
                    }
                }
                resultImage[i][j] = new Color(avgColor(red), avgColor(green), avgColor(blue));
            }
        }
        return resultImage;
    }

    private int avgColor(int value) {
        return Math.min(Math.max(0, value), 255);
    }
}
