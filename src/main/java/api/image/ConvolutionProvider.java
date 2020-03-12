package api.image;

import java.awt.*;

public interface ConvolutionProvider {
    Color[][] apply(Color[][] image, double[][] kernel);
}