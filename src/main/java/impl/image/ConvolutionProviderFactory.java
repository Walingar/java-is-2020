package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {
        return new ConvolutionProviderImpl();
    }
}