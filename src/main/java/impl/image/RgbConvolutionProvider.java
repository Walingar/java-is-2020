package impl.image;

import api.image.ConvolutionProvider;
import java.awt.*;

public class RgbConvolutionProvider implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        var imageRows = image.length;
        var imageColumns = image[0].length;
        var kernelHeightRadius = kernel.length / 2;
        var kernelWidthRadius = kernel[0].length / 2;
        var filteredImage = new Color[imageRows][imageColumns];

        for (var i = 0; i < imageRows; i++) {
            for (var j = 0; j < imageColumns; j++) {
                var redComponent = 0;
                var greenComponent = 0;
                var blueComponent = 0;

                for (var n = -kernelHeightRadius; n <= kernelHeightRadius; n++) {
                    for (var m = -kernelWidthRadius; m <= kernelWidthRadius; m++) {
                        var rowIndex = i + n;
                        var columnIndex = j + m;

                        if (rowIndex >= 0 && rowIndex < imageRows && columnIndex >= 0 && columnIndex < imageColumns) {
                            var imageColor = image[rowIndex][columnIndex];
                            var kernelValue = kernel[n + kernelHeightRadius][m + kernelWidthRadius];
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