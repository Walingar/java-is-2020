package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
//      Here we assume that kernel is rectangular and has same height and width
        int kernelRadius = kernel.length / 2;
        int columnLength = image.length;
        var result = new Color[columnLength][];

        for (var i = 0; i < columnLength; i++) {
            int rowLength = image[i].length;
            result[i] = new Color[rowLength];
            for (var j = 0; j < rowLength; j++) {

                int r = 0;
                int g = 0;
                int b = 0;
                for (var y = 0; y < kernel.length; y++) {
                    for (var x = 0; x < kernel[y].length; x++) {

                        int imageX = j + kernelRadius - x;
                        int imageY = i + kernelRadius - y;

                        if (imageX < 0 || imageX >= rowLength ||
                                imageY < 0 || imageY >= columnLength) {
                            continue;
                        }

                        Color imageColor = image[imageY][imageX];
                        double kernelPixel = kernel[y][x];
                        r += imageColor.getRed() * kernelPixel;
                        g += imageColor.getGreen() * kernelPixel;
                        b += imageColor.getBlue() * kernelPixel;
                    }
                }
                result[i][j] = new Color(r, g, b);
            }
        }
        return result;
    }
}
