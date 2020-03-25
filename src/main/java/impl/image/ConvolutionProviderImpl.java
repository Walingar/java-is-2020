package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    private static int doConstrain(int val, int min, int max) {
        return Math.min(max, Math.max(min, val));
    }

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int imgageRows = image.length;
        if (imgageRows == 0) {
            return new Color[0][0];
        }
        int imgageCols = image[0].length;

        int kernelRows = kernel.length;
        int kernelCols = 0;
        if (kernelRows > 0) {
            kernelCols = kernel[0].length;
        }

        Color[][] processedImage = new Color[imgageRows][imgageCols];

        int halfKernelRows = kernelRows / 2;
        int halfKernelCols = kernelCols / 2;
        for (int imgRow = 0; imgRow < imgageRows; imgRow++) {
            for (int imgCol = 0; imgCol < imgageCols; imgCol++) {

                int r = 0;
                int g = 0;
                int b = 0;

                for (int imgRowShift = -halfKernelRows, kernelRow = kernelRows - 1; kernelRow >= 0; imgRowShift++, kernelRow--) {
                    if (imgRow + imgRowShift < 0 || imgRow + imgRowShift >= imgageRows) {
                        continue;
                    }
                    for (int imgColShift = -halfKernelCols, kernelCol = kernelCols - 1; kernelCol >= 0; imgColShift++, kernelCol--) {
                        if (imgCol + imgColShift < 0 || imgCol + imgColShift >= imgageCols) {
                            continue;
                        }
                        double kernelElement = kernel[kernelRow][kernelCol];
                        Color pixel = image[imgRow + imgRowShift][imgCol + imgColShift];
                        r += pixel.getRed() * kernelElement;
                        g += pixel.getGreen() * kernelElement;
                        b += pixel.getBlue() * kernelElement;
                    }
                }
                int min = 0, max = 255;
                r = doConstrain(r, min, max);
                g = doConstrain(g, min, max);
                b = doConstrain(b, min, max);
                processedImage[imgRow][imgCol] = new Color(r, g, b);
            }
        }
        return processedImage;
    }
}
