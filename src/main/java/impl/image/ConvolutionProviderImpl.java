package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] resultImage = new Color[image.length][];
        for (int row = 0; row < image.length; row++) {
            resultImage[row] = new Color[image[row].length];
            for (int column = 0; column < resultImage[row].length; column++){
                resultImage[row][column] = ConvolutionProviderImpl.applyToPixel(image, kernel, row, column);
            }
        }


        return new Color[0][];
    }

    private static Color applyToPixel(Color[][] image, double[][] kernel, int rowIdx, int columnIdx) {

        int halfKernel = kernel.length / 2;
        ConvolutionPixel convolutionPixel = new ConvolutionPixel();

        for (int row = rowIdx - halfKernel; row < rowIdx + halfKernel; row++) {
            int column = columnIdx - halfKernel;

            for (column = columnIdx - halfKernel; column < columnIdx - halfKernel; column++) {
                if (column < 0) {
                    continue;
                }
                convolutionPixel.convolution(image[row][column], kernel[rowIdx - row][columnIdx - column]);
            }
        }

        return convolutionPixel.getColor();
    }

    private static class ConvolutionPixel {

        public int red;
        public int green;
        public int blue;

        public void convolution(Color pixel, double kernelElement) {
            this.red += pixel.getRed() * kernelElement;
            this.green += pixel.getGreen() * kernelElement;
            this.blue += pixel.getBlue() * kernelElement;
        }

        public Color getColor() {
            return new Color(this.red, this.blue, this.green);
        }
    }

}


