package impl.image;

import api.image.ImageConverter;

public class ImageConverterFactory {
    public static ImageConverter getInstance() {
        return new ImageConveterImplementation();   // возвращаем объект, в котором реализован интерфейс ImageConverter
    }
}