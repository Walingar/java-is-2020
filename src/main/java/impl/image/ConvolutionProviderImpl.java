package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] picture = new Color[image.length][image[0].length];
        int mid = kernel.length / 2;
        Color[][] imagelong = new Color[image.length + 2 * mid][image[0].length + 2 * mid];

        for (int row = 0; row < image.length; row++) {
            System.arraycopy(image[row], 0, imagelong[row + mid], mid, image[row].length);
        }

        for (int row = 0; row <= imagelong.length - kernel.length; row++) {
            for (int column = 0; column <= imagelong[row].length - kernel.length; column++) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;
                int temprow = row;

                for (double[] doubles : kernel) {
                    int tempcolumn = column;

                    for (double aDouble : doubles) {

                        Color currentSell = imagelong[temprow][tempcolumn];
                        if (currentSell != null) {
                            sumRed += aDouble * currentSell.getRed();
                            sumBlue += aDouble * currentSell.getBlue();
                            sumGreen += aDouble * currentSell.getGreen();
                        }

                        tempcolumn++;
                    }

                    temprow++;
                }

                picture[row][column] = new Color(Math.min(Math.max(sumRed, 0), 255),
                        Math.min(Math.max(sumGreen, 0), 255), Math.min(Math.max(sumBlue, 0), 255));

            }
        }
        return picture;
    }
}

