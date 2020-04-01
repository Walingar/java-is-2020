package impl.image;

import api.image.ConvolutionProvider;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {
        return new ConvolutionProviderImplementation();  // returns array with ConvolutionProvider implementation
    }
}