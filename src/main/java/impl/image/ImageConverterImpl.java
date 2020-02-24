package impl.image;

        import api.image.ImageConverter;

        import java.awt.*;

class ImageConverterImpl implements ImageConverter {


    public Color[][] convertToColor(int[][] image) {

        var imgHeight = image.length;
        var imgWidth = image[0].length;
        Color[][] outputImage = new Color[imgHeight][imgWidth];

        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {

                outputImage[i][j] = new Color(image[i][j]);
            }

        }

        return outputImage;
    }

    public int[][] convertToRgb(Color[][] image) {
        var imgHeight = image.length;
        var imgWidth = image[0].length;
        int[][] outputImage = new int[imgHeight][imgWidth];

        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {

                outputImage[i][j] = image[i][j].getRGB();

            }

        }

        return outputImage;
    }
}
