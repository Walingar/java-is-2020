package api.image.impl;

import api.image.ConvolutionProvider;
import model.ImageData;
import model.KernelData;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        if (image.length == 0) {
            return new Color[0][];
        }

        ImageData imageData = new ImageData(image);
        KernelData kernelData = new KernelData(kernel);
        Color[][] resultImage = new Color[imageData.getHeight()][imageData.getWidth()];
        for (var xId = 0; xId < imageData.getHeight(); xId++) {
            for (var yId = 0; yId < imageData.getWidth(); yId++) {
                resultImage[xId][yId] = calculatePixel(imageData, kernelData, xId, yId);
            }
        }
        return resultImage;
    }

    private static Color calculatePixel(ImageData imageData, KernelData kernelData, int x, int y) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int xDelta = kernelData.getHeight() / 2;
        int yDelta = kernelData.getWidth() / 2;

        for (int xId = -xDelta; xId <= xDelta; xId++) {
            int shiftedX = x + xId;
            if (shiftedX < 0 || shiftedX >= imageData.getHeight()) {
                continue;
            }
            for (int yId = -yDelta; yId <= yDelta; yId++) {
                int shiftedY = y + yId;
                if (shiftedY < 0 || shiftedY >= imageData.getWidth()) {
                    continue;
                }

                double multiplier = kernelData.getMatrix()[xId + xDelta][yId + yDelta];
                Color pixel = imageData.getMatrix()[shiftedX][shiftedY];
                red += multiplier * pixel.getRed();
                blue += multiplier * pixel.getBlue();
                green += multiplier * pixel.getGreen();
            }
        }
        red = bound(red);
        green = bound(green);
        blue = bound(blue);
        return new Color(red, green, blue);
    }

    private static int bound(int rgbValue) {
        return max(min(rgbValue, 255), 0);
    }

}
