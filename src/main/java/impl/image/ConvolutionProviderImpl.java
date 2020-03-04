package impl.image;

import api.image.ConvolutionProvider;
import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] resultImage = new Color[image.length][];
        for (int row = 0; row < image.length; row++) {
            resultImage[row] = new Color[image[row].length];
            for (int column = 0; column < resultImage[row].length; column++) {
                resultImage[row][column] = applyToPixel(image, kernel, row, column);
            }
        }
        return resultImage;
    }

    private static Color applyToPixel(Color[][] image, double[][] kernel, int rowIdx, int columnIdx) {
        int halfKernel = kernel.length / 2;
        ConvolutionPixel convolutionPixel = new ConvolutionPixel();
        for (int shiftedRow = - halfKernel; shiftedRow <= halfKernel; shiftedRow++) {
            int row = shiftedRow + rowIdx;
            if (row < 0) {
                continue;
            } else if (row >= image.length) {
                break;
            }
            for (int shiftedColumn =  -halfKernel; shiftedColumn <= halfKernel; shiftedColumn++) {
                int column = shiftedColumn + columnIdx;
                if (column < 0) {
                    continue;
                } else if (column >= image[row].length) {
                    break;
                }
                convolutionPixel.convolution(image[row][column], kernel[halfKernel + shiftedRow][halfKernel + shiftedColumn]);
            }
        }
        return convolutionPixel.getColor();
    }

    private static class ConvolutionPixel {

        private int red;
        private int green;
        private int blue;

        public void convolution(Color pixel, double kernelElement) {
            red += pixel.getRed() * kernelElement;
            green += pixel.getGreen() * kernelElement;
            blue += pixel.getBlue() * kernelElement;
        }

        public Color getColor() {
            return new Color(this.red, this.green, this.blue);
        }
    }
}