package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {

    public Color[][] apply(Color[][] image, double[][] kernel) {
        int n = image.length;
        int m = image[0].length;
        int kernelN = kernel.length;
        int kernelM = kernel[0].length;
        int HalfKernelN = kernelN / 2;
        int HalfKernelM = kernelM / 2;
        Color[][] img = new Color[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int blue = 0;
                int red = 0;
                int green = 0;
                for (int iterator1 = -HalfKernelN; iterator1 <= HalfKernelN; iterator1++) {
                    for (int iterator2 = -HalfKernelM; iterator2 <= HalfKernelM; iterator2++) {
                        if (j + iterator2 < 0 || i + iterator1 < 0 || j + iterator2 >= m || i + iterator1 >= n) {
                            continue;
                        }
                        double kernelVal = kernel[iterator1 + HalfKernelN][iterator2 + HalfKernelM];
                        Color imgVal = image[i + iterator1][j + iterator2];
                        blue += (int) (kernelVal * imgVal.getBlue());
                        green += (int) (kernelVal * imgVal.getGreen());
                        red += (int) (kernelVal * imgVal.getRed());
                    }
                }
                img[i][j] = new Color(red, green, blue, 255);
            }
        }
        return img;
    }

}