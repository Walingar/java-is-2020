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

        if(kernelWidth <=0 || (kernelWidth & 1) != 1)
            throw new IllegalArgumentException("Kernel must have odd width");

        if(kernelHeight <=0 || (kernelHeight & 1) != 1)
            throw new IllegalArgumentException("Kernel must have odd height");

        int imageHeight = image.length;
        int imageWidth = image[0].length;
        int redValue = 0;
        int greenValue = 0;
        int blueValue = 0;
        int kernelWidthRadius = kernelWidth/2;
        int kernelHeightRadius = kernelHeight/2;

        Color[][] output = new Color[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                redValue = 0;
                greenValue = 0;
                blueValue = 0;
                for (int k = 0; k <kernelHeight ; k++) {
                    for (int l = 0; l < kernelWidth; l++) {
                        if(!isInside(i+k-kernelHeightRadius,imageHeight,j+l-kernelWidthRadius,imageWidth))
                            continue;
                        redValue += kernel[k][l] * image[i+k-kernelHeightRadius][j+l-kernelWidthRadius].getRed();
                        greenValue += kernel[k][l] * image[i+k-kernelHeightRadius][j+l-kernelWidthRadius].getGreen();
                        blueValue += kernel[k][l] * image[i+k-kernelHeightRadius][j+l-kernelWidthRadius].getBlue();
                    }
                }
                output[i][j] = new Color(redValue,greenValue,blueValue);
            }
        }
        return output;

    }

    private boolean isInside(int wValue,int width,int hValue, int height){
        if(hValue < 0 || hValue >= height || wValue <0 || wValue >= width)
            return false;

        return true;
    }
}
