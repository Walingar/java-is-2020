package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;

        Color[][] resultImage = new Color[imageHeight][imageWidth];

        for (int imageRowNumber = 0; imageRowNumber < imageHeight; imageRowNumber++) {
            for (int imageColumnNumber = 0; imageColumnNumber < imageWidth; imageColumnNumber++) {
                Color resultColor = applyKernel(image, kernel, imageRowNumber, imageColumnNumber, imageWidth, imageHeight);

                resultImage[imageRowNumber][imageColumnNumber] = resultColor;
            }
        }

        return resultImage;
    }

    private Color applyKernel(Color[][] image, double[][] kernel, int imageRowNumber, int imageColumnNumber,
                              int imageWidth, int imageHeight) {
        int redAccumulator = 0;
        int greenAccumulator = 0;
        int blueAccumulator = 0;

        int kernelRadius = kernel.length / 2;

        for (int verticalShift = -kernelRadius; verticalShift <= kernelRadius; verticalShift++) {
            int verticalPosition = imageRowNumber + verticalShift;

            if (verticalPosition >= 0 && verticalPosition < imageHeight) {
                for (int horizontalShift = -kernelRadius; horizontalShift <= kernelRadius; horizontalShift++) {
                    int horizontalPosition = imageColumnNumber + horizontalShift;

                    if (horizontalPosition >= 0 && horizontalPosition < imageWidth) {
                        int kernelY = verticalShift + kernelRadius;
                        int kernelX = horizontalShift + kernelRadius;

                        double kernelValue = kernel[kernelY][kernelX];
                        Color imageColor = image[verticalPosition][horizontalPosition];

                        redAccumulator += imageColor.getRed() * kernelValue;
                        greenAccumulator += imageColor.getGreen() * kernelValue;
                        blueAccumulator += imageColor.getBlue() * kernelValue;
                    }
                }
            }
        }

        int redComponent = normalizeColorComponent(redAccumulator);
        int greenComponent = normalizeColorComponent(greenAccumulator);
        int blueComponent = normalizeColorComponent(blueAccumulator);

        return new Color(redComponent, greenComponent, blueComponent, 255);
    }

    private int normalizeColorComponent(int colorComponent) {
        int normalizedComponent = Math.max(0, colorComponent);
        normalizedComponent = Math.min(normalizedComponent, 255);

        return normalizedComponent;
    }
}
