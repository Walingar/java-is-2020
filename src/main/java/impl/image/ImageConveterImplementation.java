package impl.image;

import api.image.ImageConverter;

import java.awt.*;

public class ImageConveterImplementation implements ImageConverter {

    @Override
    public Color[][] convertToColor(int[][] image) {
        Color[][] imageColor = new Color[image.length][image[0].length];
        int colorLength = 32;
        int alphaRightBit = 8;
        int RedRightBit = 16;
        int GreenRightBit = 24;
        int base = 2;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                String tempStr = Integer.toBinaryString(image[i][j]);
                while (tempStr.length() < colorLength) {
                    tempStr = "0" + tempStr;
                }
                String alpha = tempStr.substring(0, alphaRightBit);
                String red = tempStr.substring(alphaRightBit, RedRightBit);
                String green = tempStr.substring(RedRightBit, GreenRightBit);
                String blue = tempStr.substring(GreenRightBit);
                imageColor[i][j] = new Color(Integer.parseInt(red, base), Integer.parseInt(green, base), Integer.parseInt(blue, base), Integer.parseInt(alpha, base));
            }
        }
        return imageColor;
    }

    @Override
    public int[][] convertToRgb(Color[][] image) {
        int[][] imageInt = new int[image.length][image[0].length];
        int colorLength = 8;
        int base = 2;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
//                imageInt[i][j] = image[i][j].getRGB();
                String alpha = Integer.toBinaryString(image[i][j].getAlpha());
                String red = Integer.toBinaryString(image[i][j].getRed());
                String green = Integer.toBinaryString(image[i][j].getGreen());
                String blue = Integer.toBinaryString(image[i][j].getBlue());

                while (alpha.length() < colorLength) {
                    alpha = "0" + alpha;
                }
                while (red.length() < colorLength) {
                    red = "0" + red;
                }
                while (green.length() < colorLength) {
                    green = "0" + green;
                }
                while (blue.length() < colorLength) {
                    blue = "0" + blue;
                }

                String tempStr = alpha + red + green + blue;
                try {
                    imageInt[i][j] = Integer.parseInt(tempStr, base);
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
                    imageInt[i][j] = -(Integer.parseInt(tempStr2, base) + 1);
                }
            }
        }
        return imageInt;
    }
}
