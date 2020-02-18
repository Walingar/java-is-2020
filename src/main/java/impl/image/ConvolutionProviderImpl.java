package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {

        checkSizes(image, kernel);

        Color[][] outputImage = GetImageCopy(image);

        for (int i = 0; i < outputImage.length; i++) {
            for (int j = 0; j < outputImage[i].length; j++) {
                outputImage[i][j] = applyKernel(i, j, image, kernel);
            }
        }

        return outputImage;
    }

    private Color applyKernel(int rowIndex, int columnIndex, Color[][] image, double[][] kernel) {
        int redValue = 0;
        int greenValue = 0;
        int blueValue = 0;

        int imageHeight = image.length;
        int imageWidth = image[0].length;
        int kernelHeight = kernel.length;
        int kernelWidth = kernel[0].length;
        int halfKernelHeight = kernelHeight / 2;
        int halfKernelWidth = kernelWidth / 2;

        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                int pixelPointHeight = rowIndex + i - halfKernelHeight;
                int pixelPointWidth = columnIndex + j - halfKernelWidth;

                if (isIndexInvalid(pixelPointHeight, pixelPointWidth, imageHeight, imageWidth)) {
                    continue;
                }

                Color currentPixel = image[pixelPointHeight][pixelPointWidth];
                double currentKernelValue = kernel[i][j];
                redValue += currentPixel.getRed() * currentKernelValue;
                greenValue += currentPixel.getGreen() * currentKernelValue;
                blueValue += currentPixel.getBlue() * currentKernelValue;

            }
        }



        Color resultColor = getResultColor(redValue, greenValue, blueValue);
        return resultColor;
    }

    private Color getResultColor(int redValue, int greenValue, int blueValue) {
        return new Color(Math.min(redValue, 255), Math.min(greenValue, 255), Math.min(blueValue, 255), 255);
    }

    private boolean isIndexInvalid(int rowIndex, int columnIndex, int imageHeight, int imageWidth) {
        return rowIndex < 0 || rowIndex >= imageHeight || columnIndex < 0 || columnIndex >= imageWidth;
    }


    private void checkSizes(Color[][] image, double[][] kernel) {

        int imageHeight = image.length;
        int imageWidth = image[0].length;
        int kernelHeight = kernel.length;
        int kernelWidth = kernel[0].length;

        int tempImageWidth = 0;
        for (int i = 0; i < imageHeight; i++) {
            tempImageWidth = image[i].length;
            if (imageWidth != tempImageWidth) {
                throw new IllegalArgumentException("Sizes of input image are not rectangle-shaped!");
            }
        }

        int tempKernelWidth = 0;
        for (int i = 0; i < kernelHeight; i++) {
            tempKernelWidth = kernel[i].length;
            if (kernelWidth != tempKernelWidth) {
                throw new IllegalArgumentException("Sizes of input kernel are not rectangle-shaped!");
            }
        }
    }

    private Color[][] GetImageCopy(Color[][] inputImage) {
        var outputPicture = new Color[inputImage.length][];

        for (int i = 0; i < outputPicture.length; i++) {
            outputPicture[i] = new Color[inputImage[i].length];
        }

        return outputPicture;
    }
}
