package impl.image;

import api.image.ConvolutionProvider;
import java.awt.*;

public class RgbConvolutionProvider implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        var imageRows = image.length;
        var imageColumns = image[0].length;
        var kernelRows = kernel.length;
        var kernelColumns = kernel[0].length;
        var filteredImage = new Color[imageRows][imageColumns];

        for (var i = 0; i < imageRows; i++) {
            for (var j = 0; j < imageColumns; j++) {
                var redComponent = 0;
                var greenComponent = 0;
                var blueComponent = 0;

                for (var n = 0; n < kernelRows; n++) {
                    for (var m = 0; m < kernelColumns; m++) {
                        var rowIndex = i - n + 1;
                        var columnIndex = j - m + 1;

                        if (rowIndex >= 0 && rowIndex < imageRows && columnIndex >= 0 && columnIndex < imageColumns) {
                            var imageColor = image[rowIndex][columnIndex];
                            var kernelValue = kernel[n][m];
                            redComponent += imageColor.getRed() * kernelValue;
                            greenComponent += imageColor.getGreen() * kernelValue;
                            blueComponent += imageColor.getBlue() * kernelValue;
                        }
                    }
                }

                filteredImage[i][j] = new Color(redComponent, greenComponent, blueComponent);
            }
        }

        return filteredImage;
    }
}