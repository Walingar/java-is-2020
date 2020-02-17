package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
//      Here we assume that kernel is rectangular and has same height and width
        int kernelRadius = kernel.length / 2;

//        var result = image.clone(); //Yeah, doesn't work since it's not true 2d and we'd end up with same values
        var result = new Color[image.length][];

        for (var i = 0; i < image.length; i++) {
            result[i] = new Color[image[i].length];
            for (var j = 0; j < image[i].length; j++) {

                int r = 0, g = 0, b = 0;
                for (var y = 0; y < kernel.length; y++) {
                    for (var x = 0; x < kernel[y].length; x++) {

                        int imageX = j + kernelRadius - x;
                        int imageY = i + kernelRadius - y;

                        if (imageX < 0 || imageX >= image[i].length ||
                                imageY < 0 || imageY >= image.length)
                            continue;

                        r += image[imageY][imageX].getRed() * kernel[y][x];
                        g += image[imageY][imageX].getGreen() * kernel[y][x];
                        b += image[imageY][imageX].getBlue() * kernel[y][x];
                    }
                }
                result[i][j] = new Color(r, g, b);
            }
        }
        return result;
    }
}
