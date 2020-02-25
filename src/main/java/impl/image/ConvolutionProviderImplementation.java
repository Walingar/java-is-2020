package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImplementation implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] bluredImage = new Color[image.length][image[0].length];
        int size = kernel.length;
        int step = (int) Math.floor(size / 2);
        boolean edge;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                // bluring
//                int red = (int)( Math.round(image[i-step][j-step].getRed()*kernel[size][size]) + Math.round(image[i-step][j-step].getRed()*kernel[size][size]));
                edge = false;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int m1 = -step, m2 = size - 1; m1 <= i + step && m2 >= 0; m1++, m2--) {
                    for (int n1 = -step, n2 = size - 1; n1 <= j + step && n2 >= 0; n1++, n2--) {
                        if (i + m1 < 0 || i + m1 > image.length || i + n1 < 0 || i + n1 > image[0].length) {
                            red = 0;
                            green = 0;
                            blue = 0;
                            edge = true;
                            break;
                        }
                        red += (int) Math.round(image[i + m1][j + n1].getRed() * kernel[m2][n2]);
                        green += (int) Math.round(image[i + m1][j + n1].getGreen() * kernel[m2][n2]);
                        blue += (int) Math.round(image[i + m1][j + n1].getBlue() * kernel[m2][n2]);
                    }
                    if (edge) {
                        break;
                    }
                }

                bluredImage[i][j] = new Color(red, green, blue, 255);


//                свертка
//                for (int m = 0; m < kernel.length; m++) {
//                    for (int n = 0; n < kernel.length; n++) {
//                        int red = Math.round((float) (kernel[kernel.length - m][kernel.length - n] * image[m + i][n + j].getRed()));
//                        int green = Math.round((float) (kernel[kernel.length - m][kernel.length - n] * image[m + i][n + j].getGreen()));
//                        int blue = Math.round((float) (kernel[kernel.length - m][kernel.length - n] * image[m + i][n + j].getBlue()));
//
//                    }
//                }
            }
        }

        return bluredImage;
    }
}
