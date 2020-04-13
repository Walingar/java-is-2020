package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderImpl implements ConvolutionProvider {
    public Color[][] apply(Color[][] image, double[][] kernel) {
        int raw = image.length;
        int col = image[0].length;
        int k_size = kernel.length;
        Color[][] new_color = new Color[raw][col];

        for (int i = 0; i < raw; i++) {
            for (int j = 0; j < col; j++) {
                int r = 0;
                int g = 0;
                int b = 0;

                for (int k_i = 0; k_i < k_size; k_i++) {
                    for (int k_j = 0; k_j < k_size; k_j++) {
                        int pix_i = i - k_size / 2 + k_i;
                        int pix_j = j - k_size / 2 + k_j;

                        if ((pix_i >= 0 && pix_i < raw) && (pix_j >= 0 && pix_j < col)) {
                            r += image[pix_i][pix_j].getRed() * kernel[k_i][k_j];
                            g += image[pix_i][pix_j].getGreen() * kernel[k_i][k_j];
                            b += image[pix_i][pix_j].getBlue() * kernel[k_i][k_j];
                        }
                    }
                }

                new_color[i][j] = new Color(r, g, b);
            }
        }

        return new_color;
    }
}