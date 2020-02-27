package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;
        int kernelHeight = kernel.length / 2;
        int kernelWith = kernel[0].length / 2;

        Color[][] resultImage = new Color[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int tempR = 0;
                int tempG = 0;
                int tempB = 0;
                for (int kernelRowIndex = -kernelHeight; kernelRowIndex <= kernelHeight; kernelRowIndex++) {
                    for (int kernelColumnIndex = -kernelWith; kernelColumnIndex <= kernelWith; kernelColumnIndex++) {
                        int rowIndex = i + kernelRowIndex;
                        int columnIndex = j + kernelColumnIndex;
                        if (rowIndex >= 0 && columnIndex >= 0 && rowIndex < imageHeight && columnIndex < imageWidth) {
                            Color imageColor = image[rowIndex][columnIndex];
                            double sourceKernel = kernel[kernelRowIndex + kernelHeight][kernelColumnIndex + kernelWith];

                            tempR += imageColor.getRed() * sourceKernel;
                            tempG += imageColor.getGreen() * sourceKernel;
                            tempB += imageColor.getBlue() * sourceKernel;
                        }
                    }
                }
                resultImage[i][j] = new Color(getResultValueFromTemp(tempR), getResultValueFromTemp(tempG), getResultValueFromTemp(tempB));
            }
        }
        return resultImage;
    }

    private int getResultValueFromTemp(int value) {
        return Math.min(Math.max(0, value), 255);
    }
}

