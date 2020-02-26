package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConveterImplementation implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] imageColor = new Color[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                String tempStr = Integer.toBinaryString(image[i][j]);
                while (tempStr.length() < 32) {
                    tempStr = "0" + tempStr;
                }
                String alpha = tempStr.substring(0, 8);
                String red = tempStr.substring(8, 16);
                String green = tempStr.substring(16, 24);
                String blue = tempStr.substring(24);
                imageColor[i][j] = new Color(Integer.parseInt(red, 2), Integer.parseInt(green, 2), Integer.parseInt(blue, 2), Integer.parseInt(alpha, 2));
            }
        }
        return imageColor;
    }

    ;

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] imageInt = new int[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
//                imageInt[i][j] = image[i][j].getRGB();
                String alpha = Integer.toBinaryString(image[i][j].getAlpha());
                String red = Integer.toBinaryString(image[i][j].getRed());
                String green = Integer.toBinaryString(image[i][j].getGreen());
                String blue = Integer.toBinaryString(image[i][j].getBlue());

                while (alpha.length() < 8) {
                    alpha = "0" + alpha;
                }
                while (red.length() < 8) {
                    red = "0" + red;
                }
                while (green.length() < 8) {
                    green = "0" + green;
                }
                while (blue.length() < 8) {
                    blue = "0" + blue;
                }

                String tempStr = alpha + red + green + blue;
                try {
                    imageInt[i][j] = Integer.parseInt(tempStr, 2);
                } catch (NumberFormatException e) {
                    String tempStr2 = "";
                    for (int k = 0; k < tempStr.length(); k++) {
                        if (tempStr.charAt(k) == '1') {
                            tempStr2 += '0';
                        } else if (tempStr.charAt(k) == '0') {
                            tempStr2 += '1';
                        } else {
                            System.out.println("Error!");
                            return null;
                        }
                    }
                    imageInt[i][j] = -(Integer.parseInt(tempStr2, 2) + 1);
                }
          }
        }
        return imageInt;
    }

    ;
}
