package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] filteredImage = new Color[image.length][];
        for (int i = 0; i < image.length; i++) {
            filteredImage[i] = new Color[image[i].length];
            for (int j = 0; j < image[i].length; j++) {
                filteredImage[i][j] = applyKernelToPixel(image, kernel, i, j);
            }
        }
        return filteredImage;
    }

    private Color applyKernelToPixel(Color[][] image, double[][] kernel, int pixelRow, int pixelColumn) {
        ConvolutionPixel convolutionPixel = new ConvolutionPixel();
        int kernelHalfSize = kernel.length / 2;
        for (int rowShift = -kernelHalfSize; rowShift <= kernelHalfSize; rowShift++) {
            for (int columnShift = -kernelHalfSize; columnShift <= kernelHalfSize; columnShift++) {
                int currentPixelRow = pixelRow + rowShift;
                int currentPixelColumn = pixelColumn + columnShift;
                boolean currentPixelRowOutOfBounds = currentPixelRow < 0 || currentPixelRow >= image.length;
                boolean currentPixelColumnOutOfBounds = currentPixelColumn < 0 || currentPixelColumn >= image[0].length;
                if (currentPixelRowOutOfBounds || currentPixelColumnOutOfBounds) {
                    continue;
                }
                Color imagePixel = image[currentPixelRow][currentPixelColumn];
                double kernelElement = kernel[kernelHalfSize + rowShift][kernelHalfSize + columnShift];
                convolutionPixel.apply(imagePixel, kernelElement);
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
