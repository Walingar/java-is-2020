package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    public Color[][] apply(Color[][] image, double[][] kernel) {
        var imgHeight = image.length;
        var imgWidth = image[0].length;
        var kernelHeight = kernel.length / 2;
        var kernelWith = kernel[0].length / 2;
        var processedImg = new Color[imgHeight][imgWidth];

        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                var R = 0;
                var G = 0;
                var B = 0;

                for (int k = -kernelHeight; k <= kernelHeight; k++) {
                    for (int p = -kernelWith; p <= kernelWith; p++) {
                        int row = i + k;
                        int column = j + p;

                        if (row >= 0 && row < imgHeight && column >= 0 && column < imgWidth) {
                            var bufColor = image[row][column];
                            var bufKernel = kernel[k + kernelHeight][p + kernelWith];
                            R += bufColor.getRed() * bufKernel;
                            G += bufColor.getGreen() * bufKernel;
                            B += bufColor.getBlue() * bufKernel;
                        }
                    }
                }
                processedImg[i][j] = new Color(R, G, B);
            }
        }
        return processedImg;
    }
}
