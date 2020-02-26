package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {
        return new ConvolutionProviderImpl();
    }

    public static class ConvolutionProviderImpl implements ConvolutionProvider {

        @Override
        public Color[][] apply(Color[][] image, double[][] kernel) {
            int img_rows = image.length;
            if (img_rows == 0) {
                return new Color[0][0];
            }
            int img_cols = image[0].length;

            int kernel_rows = kernel.length;
            int kernel_cols = 0;
            if (kernel_rows > 0) {
                kernel_cols = kernel[0].length;
            }

            Color[][] processed_img = new Color[img_rows][img_cols];

            int half_kernel_rows = kernel_rows / 2;
            int half_kernel_cols = kernel_cols / 2;
            for (int img_row = 0; img_row < img_rows; img_row++) {
                for (int img_col = 0; img_col < img_cols; img_col++) {

                    int r = 0;
                    int g = 0;
                    int b = 0;

                    for (int img_row_shift = -half_kernel_rows, kernel_row = kernel_rows - 1; kernel_row >= 0; img_row_shift++, kernel_row--) {
                        if (img_row + img_row_shift < 0 || img_row + img_row_shift >= img_rows) {
                            continue;
                        }
                        for (int img_col_shift = -half_kernel_cols, kernel_col = kernel_cols - 1; kernel_col >= 0; img_col_shift++, kernel_col--) {
                            if (img_col + img_col_shift < 0 || img_col + img_col_shift >= img_cols) {
                                continue;
                            }
                            double kernel_el = kernel[kernel_row][kernel_col];
                            Color pixel = image[img_row + img_row_shift][img_col + img_col_shift];
                            r += pixel.getRed() * kernel_el;
                            g += pixel.getGreen() * kernel_el;
                            b += pixel.getBlue() * kernel_el;
                        }
                    }
                    r = Math.min(255, Math.max(0, r));
                    g = Math.min(255, Math.max(0, g));
                    b = Math.min(255, Math.max(0, b));
                    processed_img[img_row][img_col] = new Color(r, g, b);
                }
            }
            return processed_img;
        }
    }
}
