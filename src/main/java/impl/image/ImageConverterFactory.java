package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterFactory {
    public static ImageConverter getInstance() {
        return new ImageConverterImpl();
    }
}