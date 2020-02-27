package api.image;

import java.awt.Color;

public interface ConvolutionProvider {

  Color[][] apply(Color[][] image, double[][] kernel);
}