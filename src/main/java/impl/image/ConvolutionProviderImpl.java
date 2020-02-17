package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] filteredImage = new Color[image.length][];
        for (int rowIndex = 0; rowIndex < image.length; rowIndex++) {
            Color[] row = image[rowIndex];
            filteredImage[rowIndex] = new Color[row.length];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                filteredImage[rowIndex][columnIndex] = applyKernelToPixel(image, kernel, rowIndex, columnIndex);
            }
        }
        return filteredImage;
    }

    private Color applyKernelToPixel(Color[][] image, double[][] kernel, int pixelRow, int pixelColumn) {
        ConvolutionPixel convolutionPixel = new ConvolutionPixel();
        int kernelHalfSize = kernel.length / 2;
        for (int rowShift = -kernelHalfSize; rowShift <= kernelHalfSize; rowShift++) {
            for (int columnShift = -kernelHalfSize; columnShift <= kernelHalfSize; columnShift++) {
                int rowIndex = pixelRow + rowShift;
                if (rowIndex >= 0 && rowIndex < image.length) {
                    Color[] row = image[rowIndex];
                    int columnIndex = pixelColumn + columnShift;
                    if (columnIndex >= 0 && columnIndex < row.length) {
                        double kernelElement = kernel[kernelHalfSize + rowShift][kernelHalfSize + columnShift];
                        convolutionPixel.apply(row[columnIndex], kernelElement);
                    }
                }
            }
        }
        return convolutionPixel.toColor();
    }

    private static class ConvolutionPixel {
        private int red;
        private int green;
        private int blue;

        public void apply(Color imagePixel, double kernelElement) {
            red += imagePixel.getRed() * kernelElement;
            green += imagePixel.getGreen() * kernelElement;
            blue += imagePixel.getBlue() * kernelElement;
        }

        public Color toColor() {
            int red = boundColorComponent(this.red);
            int green = boundColorComponent(this.green);
            int blue = boundColorComponent(this.blue);
            return new Color(red, green, blue);
        }

        private int boundColorComponent(int colorComponent) {
            return max(0, min(colorComponent, 255));
        }
    }
}
