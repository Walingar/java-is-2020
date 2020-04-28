package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int raw = image.length;
        int col = image[0].length;
        int kSize = kernel.length;
        Color[][] newColor = new Color[raw][col];

        for (int i = 0; i < raw; i++) {
            for (int j = 0; j < col; j++) {
                int r = 0;
                int g = 0;
                int b = 0;

                for (int iKernel = 0; iKernel < kSize; iKernel++) {
                    for (int jKernel = 0; jKernel < kSize; jKernel++) {
                        int iPix = i - kSize / 2 + iKernel;
                        int jPix = j - kSize / 2 + jKernel;

                        if ((iPix >= 0 && iPix < raw) && (jPix >= 0 && jPix < col)) {
                            r += image[iPix][jPix].getRed() * kernel[iKernel][jKernel];
                            g += image[iPix][jPix].getGreen() * kernel[iKernel][jKernel];
                            b += image[iPix][jPix].getBlue() * kernel[iKernel][jKernel];
                        }
                    }
                }

                newColor[i][j] = new Color(r, g, b);
            }
        }

        return newColor;
    }
}