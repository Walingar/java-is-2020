package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class KernelConverter implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int kernelHeight = kernel.length;
        int kernelWidth = kernel[0].length;

        if (kernelWidth <= 0 || (kernelWidth & 1) != 1) {
            throw new IllegalArgumentException("Kernel must have odd width");
        }

        if (kernelHeight <= 0 || (kernelHeight & 1) != 1) {
            throw new IllegalArgumentException("Kernel must have odd height");
        }

        int imageHeight = image.length;
        int imageWidth = image[0].length;
        int kernelWidthRadius = kernelWidth / 2;
        int kernelHeightRadius = kernelHeight / 2;

        Color[][] output = new Color[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int redValue = 0;
                int greenValue = 0;
                int blueValue = 0;
                for (int k = 0; k < kernelHeight; k++) {
                    for (int l = 0; l < kernelWidth; l++) {
                        int curKernelRow = i + k - kernelHeightRadius;
                        int curKernelColumn = j + l - kernelWidthRadius;
                        if (!isInside(curKernelRow, imageHeight, curKernelColumn, imageWidth)){
                            continue;
                        }
                        double currentKernel = kernel[k][l];
                        Color currentColor = image[curKernelRow][curKernelColumn];
                        redValue += currentKernel * currentColor.getRed();
                        greenValue += currentKernel * currentColor.getGreen();
                        blueValue += currentKernel * currentColor.getBlue();
                    }
                }
                output[i][j] = new Color(redValue, greenValue, blueValue);
            }
        }
        return output;
    }

    private boolean isInside(int hValue, int height, int wValue, int width) {
        return hValue >= 0 && hValue < height && wValue >= 0 && wValue < width;
    }
}
