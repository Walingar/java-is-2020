package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImplementation implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] bluredImage;
        for (int i = 0; i < image.length; i += kernel.length) {
            for (int j = 0; j < image[0].length; j += kernel.length){
                // bluring
                for (int m = 0; m < kernel.length; m++){
                    for (int n = 0; n < kernel.length; n++){
                        int red = Math.round((float)(kernel[kernel.length-m][kernel.length-n] * image[m+i][n+j].getRed()));
                        int green = Math.round((float)(kernel[kernel.length-m][kernel.length-n] * image[m+i][n+j].getGreen()));
                        int blue = Math.round((float)(kernel[kernel.length-m][kernel.length-n] * image[m+i][n+j].getBlue()));

                    }
                }
            }
        }

        return null;
    }
}
