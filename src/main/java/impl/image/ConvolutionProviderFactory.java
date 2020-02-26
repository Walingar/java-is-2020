package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {
        ConvolutionProvider convolution = new ConvolutionProvider() {
            @Override
            public Color[][] apply(Color[][] image, double[][] kernel) {
                Color[][] picture = new Color[image.length][image[0].length];
                int mid = kernel.length/2;
                Color [][] imagelong = new Color[image.length+2*mid][image[0].length+2*mid];
                for (int row = 0; row<image.length; row++){
                    for (int column = 0; column<image[row].length; column++){
                        imagelong[row+mid][column+mid] = image[row][column];
                    }
                }
                for (int row = 0; row<=imagelong.length - kernel.length; row++){
                    for (int column = 0; column<=imagelong[row].length - kernel.length; column++){
                        int sumRed = 0;
                        int sumGreen = 0;
                        int sumBlue = 0;
                        int temprow = row;
                        for (int rowkernel = kernel.length-1; rowkernel>=0; rowkernel--) {
                            int tempcolumn = column;
                            for (int columnkernel = kernel[rowkernel].length - 1; columnkernel >= 0; columnkernel--) {
                                if (imagelong[temprow][tempcolumn] != null) {
                                    sumRed += kernel[rowkernel][columnkernel] * imagelong[temprow][tempcolumn].getRed();
                                    sumBlue += kernel[rowkernel][columnkernel] * imagelong[temprow][tempcolumn].getBlue();
                                    sumGreen += kernel[rowkernel][columnkernel] * imagelong[temprow][tempcolumn].getGreen();
                                }
                                tempcolumn++;
                            }
                            temprow++;
                        }
                        picture[row][column] = new Color(Math.min(Math.max(sumRed,0),255),
                                Math.min(Math.max(sumGreen,0),255),Math.min(Math.max(sumBlue,0),255));

                    }
                }
                return picture;
            }
        };
        return convolution;
    }
}