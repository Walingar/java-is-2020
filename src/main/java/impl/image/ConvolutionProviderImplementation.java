package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int m1 = -step, m2 = size - 1; m1 <= i + step && m2 >= 0; m1++, m2--) {
                    for (int n1 = -step, n2 = size - 1; n1 <= j + step && n2 >= 0; n1++, n2--) {

                        try{
                            red += (int) image[i + m1][j + n1].getRed() * kernel[m2][n2];
                        }catch (ArrayIndexOutOfBoundsException e){
                            red = Math.max(0, red);
                        }

                        try{
                            green += (int) image[i + m1][j + n1].getGreen() * kernel[m2][n2];
                        }catch (ArrayIndexOutOfBoundsException e){
                            green = Math.max(0, green);
                        }

                        try{
                            blue += (int) image[i + m1][j + n1].getBlue() * kernel[m2][n2];
                        }catch (ArrayIndexOutOfBoundsException e){
                            blue = Math.max(0, blue);
                        }
                    }
                }

                bluredImage[i][j] = new Color(red, green, blue, 255);

            }
        }

        return bluredImage;
    }
}
