package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

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
        Color filteredPixel = Color.BLACK;
        int kernelHalfSize = kernel.length / 2;
        for (int rowShift = -kernelHalfSize; rowShift <= kernelHalfSize; rowShift++) {
            for (int columnShift = -kernelHalfSize; columnShift <= kernelHalfSize; columnShift++) {
                int currentPixelRow = pixelRow + rowShift;
                int currentPixelColumn = pixelColumn + columnShift;
                boolean currentPixelRowOutOfBounds = currentPixelRow < 0 || currentPixelRow >= image.length;
                boolean currentPixelColumnOutOfBounds = currentPixelColumn < 0 || currentPixelColumn >= image[0].length;
                if (currentPixelRowOutOfBounds || currentPixelColumnOutOfBounds) continue;

                Color imagePixel = image[currentPixelRow][currentPixelColumn];
                double kernelElement = kernel[kernelHalfSize + rowShift][kernelHalfSize + columnShift];
                filteredPixel = new Color(
                        (int) (filteredPixel.getRed() + kernelElement * imagePixel.getRed()),
                        (int) (filteredPixel.getGreen() + kernelElement * imagePixel.getGreen()),
                        (int) (filteredPixel.getBlue() + kernelElement * imagePixel.getBlue())
                );
            }
        }
        return filteredPixel;
    }
}
