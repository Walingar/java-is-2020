package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int rowsLength = image.length;
        int columnsLength = image[0].length;
        Color[][] imageNew = new Color[rowsLength][columnsLength];
        int kernelMiddle = kernel.length / 2;

        for (int imageRow = 0; imageRow < rowsLength; imageRow++) {
            for (int imageColumn = 0; imageColumn < columnsLength; imageColumn++) {
                int imageCurrentRow = imageRow - kernelMiddle;
                int sumBlue = 0;
                int sumGreen = 0;
                int sumRed = 0;
                for (int kernelRow = kernel.length - 1; kernelRow >= 0; kernelRow--) {
                    int imageCurrentColumn = imageColumn - kernelMiddle;
                    for (int kernelColumn = kernel[0].length - 1; kernelColumn >= 0; kernelColumn--) {
                        if ((imageCurrentRow >= 0) && (imageCurrentColumn >= 0) &&
                                (imageCurrentRow < rowsLength) && (imageCurrentColumn < columnsLength)) {
                            var kernelCurrent = kernel[kernelRow][kernelColumn];
                            var imageCurrent = image[imageCurrentRow][imageCurrentColumn];
                            sumBlue += kernelCurrent * imageCurrent.getBlue();
                            sumGreen += kernelCurrent * imageCurrent.getGreen();
                            sumRed += kernelCurrent * imageCurrent.getRed();
                        }
                        imageCurrentColumn++;
                    }
                    imageCurrentRow++;
                }
                imageNew[imageRow][imageColumn] = new Color(checkNumber(sumRed),
                        checkNumber(sumGreen), checkNumber(sumBlue));
            }
        }
        return imageNew;
    }

    private int checkNumber(int num) {
        return Math.min(Math.max(0, num), 255);
    }
}
