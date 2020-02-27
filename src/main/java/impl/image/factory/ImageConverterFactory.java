package impl.image.factory;

import api.image.ImageConverter;
import api.image.impl.ImageConverterImpl;

public class ImageConverterFactory {
    public static ImageConverter getInstance() {
        return new ImageConverterImpl();
    }
}