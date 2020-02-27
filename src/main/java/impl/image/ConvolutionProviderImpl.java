package impl.image;

import api.image.ConvolutionProvider;
import java.awt.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        var height = image.length;
        if (height == 0) {
            return new Color[0][];
        }
        var imageData = new ImageData(image);
        var kernelData = new KernelData(kernel);
        var resultImage = new Color[height][imageData.width];
        for (var x = 0; x < imageData.height; x++) {
            for (var y = 0; y < imageData.width; y++) {
                resultImage[x][y] = calculatePixel(imageData, kernelData, x, y);
            }    
        }
        return resultImage;
    }

    private static Color calculatePixel(ImageData image, KernelData kernel, int x, int y) {
        var red = 0;
        var green = 0;
        var blue = 0;
        var xDelta = kernel.height / 2;
        var yDelta = kernel.width / 2;
        for (var dx = -xDelta; dx <= xDelta; dx++) {
            var shiftedX = x + dx;
            if (shiftedX < 0 || shiftedX >= image.height) {
                continue;
            }
            for (var dy = -yDelta; dy <= yDelta; dy++) {
                var shiftedY = y + dy;
                if (shiftedY < 0 || shiftedY >= image.width) {
                    continue;
                }

                var kernelMultiplier = kernel.multiplicationMatrix[dx + xDelta][dy + yDelta];
                var imagePixel = image.colorMatrix[shiftedX][shiftedY];
                red += kernelMultiplier * imagePixel.getRed();
                blue += kernelMultiplier * imagePixel.getBlue();
                green += kernelMultiplier * imagePixel.getGreen();
            }
        }
        red = bound(red);
        green = bound(green);
        blue = bound(blue);
        return new Color(red, green, blue);
    }

    private static int bound(int boundComponent) {
        return max(min(boundComponent, 255), 0);
    }

    private static class MatrixData{
        public final int height;
        public final int width;

        private MatrixData(int height, int width) {
            this.height = height;
            this.width = width;
        }

    }

    private static class ImageData extends MatrixData {
        private final Color[][] colorMatrix;

        private ImageData(Color[][] matrix) {
            super(matrix.length, matrix[0].length);
            this.colorMatrix = matrix;
        }
    }

    private static class KernelData extends MatrixData {
        private final double[][] multiplicationMatrix;

        private KernelData(double[][] matrix) {
            super(matrix.length, matrix[0].length);
            this.multiplicationMatrix = matrix;
        }
    }
}
