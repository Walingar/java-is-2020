package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConverterFactory {
    private static ImageConverterFactory instance;
    public static ImageConverterFactory getInstance() {
        if(instance==null)instance =new ImageConverterFactory();
        return instance;
    }
    public Color[][] convertToColor(int [][] image) {
        Color[][] img = new Color[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                int blue = 0, green = 0, red = 0, alpha = 255;
                for (int k = 0; k <= 23; k++) {
                    if (k <= 7) blue += ((1 << k) & (image[i][j]));
                    else if (k <= 15) green += (((1 << (k)) & (image[i][j]))>>8);
                    else if (k <= 23) red +=(((1 << (k) & (image[i][j])))>>16);
                }
                img[i][j] = new Color(red, green, blue, alpha);
            }
        }
        return img;
    }
    public int[][] convertToRgb(Color[][]image)
    {int[][] img = new int[image.length][image[0].length];
        for(int i=0;i<image.length;i++){
            for(int j=0;j<image[0].length;j++){
                int blue=image[i][j].getBlue();
                int green=image[i][j].getGreen();green=(green<<8);
                int red=image[i][j].getRed();red=(red<<16);
                for (int k=0;k<=31;k++) {
                    if (k <= 7)
                        img[i][j] += ((1 << k) & (blue));
                    else if (k <= 15)
                        img[i][j] += ((1 << k) & (green));
                    else if (k <= 23)
                        img[i][j] += ((1 << k) & (red));
                    else
                        img[i][j] += (1 << k);
                }
            }
        }
        return img;

    }


}