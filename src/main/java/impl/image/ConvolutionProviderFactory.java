package impl.image;

import api.image.ConvolutionProvider;
import api.image.impl.ConvolutionProviderImpl;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {
        return new ConvolutionProviderImpl();
    }
}