package impl.image;

public class ImageConverterFactory {
    public static ImageConverterImpl getInstance() {
        return new ImageConverterImpl();
    }
}