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
        int alpha = 255;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                // bluring
                int red = 0;
                int green = 0;
                int blue = 0;
                int m1 = -step;
                int n1 = -step;
                int m2 = size - 1;
                int n2 = size - 1;

//                while (m1 <= i + step && m2 >= 0) {
//                    while (n1 <= j + step && n2 >= 0){
//                        double kernelItem = kernel[m2][n2];
//
//                        try {
//                            red += (int) image[i + m1][j + n1].getRed() * kernelItem;
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            red = Math.max(0, red);
//                        }
//                        try {
//                            green += (int) image[i + m1][j + n1].getGreen() * kernelItem;
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            green = Math.max(0, green);
//                        }
//                        try {
//                            blue += (int) image[i + m1][j + n1].getBlue() * kernelItem;
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            blue = Math.max(0, blue);
//                        }
//                        n1++;
//                        n2--;
//                    }
//                    m1++;
//                    m2--;
//                }
                for (m1 = -step, m2 = size - 1; m1 <= i + step && m2 >= 0; m1++, m2--) {
                    for (n1 = -step, n2 = size - 1; n1 <= j + step && n2 >= 0; n1++, n2--) {
                        double kernelItem = kernel[m2][n2];
                        try {
                            red += (int) image[i + m1][j + n1].getRed() * kernelItem;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            red = Math.max(0, red);
                        }
                        try {
                            green += (int) image[i + m1][j + n1].getGreen() * kernelItem;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            green = Math.max(0, green);
                        }
                        try {
                            blue += (int) image[i + m1][j + n1].getBlue() * kernelItem;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            blue = Math.max(0, blue);
                        }
                    }
                }
                bluredImage[i][j] = new Color(red, green, blue, alpha);
            }
        }
        return bluredImage;
    }
}
