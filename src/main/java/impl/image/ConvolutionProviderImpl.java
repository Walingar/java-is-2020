package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    @Override
    public Color[][] apply(Color[][] image, double[][] core) {
        int imgTop = image.length;

        if (imgTop == 0) {
            return new Color[0][0];
        }

        int imgWidht = image[0].length;
        int coreTop = core.length / 2;
        int coreWidht = 0;

        if (coreTop > 0) {
            coreWidht = core[0].length / 2;
        }
        Color[][] resultImg = new Color[imgTop][imgWidht];

        for (int i = 0; i < imgTop; i++) {
            for (int j = 0; j < imgWidht; j++) {
                int r = 0;
                int g = 0;
                int b = 0;

                for (int coreRow = -coreTop; coreRow <= coreTop; coreRow++) {
                    for (int coreLine = -coreWidht; coreLine <= coreWidht; coreLine++) {
                        int row = i + coreRow;
                        int line = j + coreLine;
                        if (row >= 0 && line >= 0 && row < imgTop && line < imgWidht) {
                            Color pixel = image[row][line];
                            double coreEl = core[coreRow + coreTop][coreLine + coreWidht];

                            r += pixel.getRed() * coreEl;
                            g += pixel.getGreen() * coreEl;
                            b += pixel.getBlue() * coreEl;
                        }
                    }
                }
                resultImg[i][j] = new Color(result(r), result(g), result(b));
            }
        }
        return resultImg;
    }

    private int result(int value) {
        return Math.min(Math.max(0, value), 255);
    }
}
