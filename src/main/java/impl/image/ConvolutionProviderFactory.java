package impl.image;

import api.image.ConvolutionProvider;
import java.awt.*;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {
        return new ConvolutionProviderImpl();
    }

    public static class ConvolutionProviderImpl implements ConvolutionProvider {
        @Override
        public Color[][] apply(Color[][] image, double[][] core) {
            int imgTop = image.length;

            if (imgTop == 0) {
                return new Color[0][0];
            }

            int imgWight = image[0].length;
            int coreTop = core.length /2;
            int coreWight = 0;

            if (coreTop > 0) {
                coreWight = core[0].length /2;
            }
            Color[][] resultImg = new Color[imgTop][imgWight];

            for (int i = 0; i < imgTop; i++) {
                for (int j = 0; j < imgWight; j++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;

                    for (int coreRow = -coreTop; coreRow <= coreTop; coreRow++) {
                        for (int coreLine = -coreWight; coreLine <= coreWight; coreLine++) {
                            int row = i + coreRow;
                            int line = j + coreLine;
                            if (row >= 0 && line >= 0 && row < imgTop && line < imgWight) {
                                Color pixel = image[row][line];
                                double coreEl = core[coreRow + coreTop][coreLine + coreWight];

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
}