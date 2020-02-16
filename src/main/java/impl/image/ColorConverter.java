package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ColorConverter implements ImageConverter {
    @Override
    public Color[][] convertToColor(int[][] image) {
        var colorImage = new Color[image.length][image[0].length];

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                colorImage[i][j] = new Color(image[i][j],true);
            }
        }

        return colorImage;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        var intImage = new int[image.length][image[0].length];

        for(int i=0;i<image.length;i++){
            for(int j=0;j<image[i].length;j++){
                intImage[i][j] = image[i][j].getRGB();
            }
        }

        return intImage;
    }
}
