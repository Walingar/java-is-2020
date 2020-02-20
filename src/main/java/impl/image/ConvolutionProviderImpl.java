package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

class ConvolutionProviderImpl implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        var imageHeight = image.length;
        if (imageHeight == 0) {
            return new Color[imageHeight][];
        }
        var imageWidth = image[0].length;

        var wrappedImage = new ImageData(image, imageHeight, imageWidth);
        var wrappedKernel = new KernelData(kernel, kernel.length / 2, kernel[0].length / 2);

        var convertedImage = new Color[imageHeight][imageWidth];

        for (var x = 0; x < wrappedImage.height; x++) {
            for (var y = 0; y < wrappedImage.width; y++) {
                convertedImage[x][y] = applyKernelToPixel(wrappedImage, wrappedKernel, x, y);
            }
        }

        return convertedImage;
    }

    private static boolean isOutside(int length, int index) {
        return index < 0 | index >= length;
    }

    private static int boundComponent(int colorComponent) {
        return max(min(colorComponent, 255), 0);
    }

    private static Color applyKernelToPixel(ImageData image, KernelData kernel, int x, int y) {
        var red = 0;
        var green = 0;
        var blue = 0;

        for (var dx = -kernel.verticalRadius; dx <= kernel.verticalRadius; dx++) {
            var shiftedX = x - dx;
            if (isOutside(image.height, shiftedX)) {
                continue;
            }

            var imageRow = image.image[shiftedX];
            var kernelRow = kernel.kernel[dx + kernel.verticalRadius];

            for (var dy = -kernel.horizontalRadius; dy <= kernel.horizontalRadius; dy++) {
                var shiftedY = y - dy;
                if (isOutside(image.width, shiftedY)) {
                    continue;
                }

                var pixel = imageRow[shiftedY];
                var kernelElement = kernelRow[dy + kernel.horizontalRadius];

                red += pixel.getRed() * kernelElement;
                green += pixel.getGreen() * kernelElement;
                blue += pixel.getBlue() * kernelElement;
            }
        }

       return new Color(boundComponent(red), boundComponent(green), boundComponent(blue));
    }

    private static class ImageData {
        private final Color[][] image;
        private final int height;
        private final int width;

        public ImageData(Color[][] image, int height, int width) {
            this.image = image;
            this.height = height;
            this.width = width;
        }
    }

    private static class KernelData {
        private final double[][] kernel;
        private final int verticalRadius;
        private final int horizontalRadius;

        public KernelData(double[][] kernel, int verticalRadius, int horizontalRadius) {
            this.kernel = kernel;
            this.verticalRadius = verticalRadius;
            this.horizontalRadius = horizontalRadius;
        }
    }
}
