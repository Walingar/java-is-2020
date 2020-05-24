package impl.image;

import api.image.ConvolutionProvider;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int numRows = image.length;
        int numColumns = image[0].length;

        var resultImage = new Color[numRows][]

        for (var rowIdx = 0; rowIdx < numRows; rowIdx++) {
            resultImage[rowIdx] = new Color[numColumns];
            for (var colIdx = 0; colIdx < numColumns; colIdx++)
                resultImage[colIdx][rowIdx] = applyKernelToPixel(image, kernel, colIdx, rowIdx);
        }

        return resultImage;
    }

    private Color applyKernelToPixel(Color[][] image, double[][] kernel, int pixelRowIdx, int pixelColIdx) {
        int numRows = image.length;
        int numColumns = image[0].length;

        int kernelShiftRow = kernel.length / 2;
        int kernelShiftColumn = kernel[0].length / 2;

        int red = 0;
        int green = 0;
        int blue = 0;

        for (var rowShift = -kernelShiftRow; rowShift <= kernelShiftRow; rowShift++) {
            for (var colShift = -kernelShiftColumn; colShift <= kernelShiftColumn; colShift++) {
                int rowIdx = pixelRowIdx + rowShift;
                int colIdx = pixelColIdx + colShift;
                if (rowIdx >= 0 && rowIdx < numRows && colIdx >= 0 && colIdx < numColumns) {
                    double kernelPixel = kernel[rowIdx][colIdx];
                    Color imagePixelColor = image[pixelRowIdx][pixelColIdx];
                    red += imagePixelColor.getRed() * kernelPixel;
                    green += imagePixelColor.getGreen() * kernelPixel;
                    blue += imagePixelColor.getBlue() * kernelPixel;
                }
            }
        }
        return new Color(red, green, blue);
    }
}
