package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int kernelRadius = kernel.length / 2;
        int imageHeight = image.length;
        Color[][] outputImage = new Color[imageHeight][];

        for (int rowNumber = 0; rowNumber < imageHeight; rowNumber++) {
            Color[] row = image[rowNumber];
            int rowLength = row.length;
            outputImage[rowNumber] = new Color[rowLength];
            for (int columnNumber = 0; columnNumber < rowLength; columnNumber++) {
                int redValue = 0;
                int greenValue = 0;
                int blueValue = 0;
                for (int kernelHeightShift = -kernelRadius; kernelHeightShift <= kernelRadius; kernelHeightShift++) {
                    int y = rowNumber + kernelHeightShift;
                    if (y < 0 || y >= imageHeight) {
                        continue;
                    }
                    for (int kernelWidthShift = -kernelRadius; kernelWidthShift <= kernelRadius; kernelWidthShift++) {
                        int x = columnNumber + kernelWidthShift;
                        if (x < 0 || x >= rowLength) {
                            continue;
                        }
                        Color inputColor = image[y][x];
                        double inputKernel = kernel[kernelHeightShift + kernelRadius][kernelWidthShift + kernelRadius];

                        redValue += inputColor.getRed() * inputKernel;
                        greenValue += inputColor.getGreen() * inputKernel;
                        blueValue += inputColor.getBlue() * inputKernel;
                    }
                }
                outputImage[rowNumber][columnNumber] = new Color(redValue, greenValue, blueValue, 255);
            }
        }
        return outputImage;
    }
}