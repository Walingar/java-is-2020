package impl.image;

import api.image.ImageConverter;

import java.awt.*;

//        alpha                       red                      green                     blue
//0  1  2  3  4  5  6  7    8  9  10 11 12 13 14 15   16 17 18 19 20 21 22 23   24 25 26 27 28 29 30 31
//1  1  1  1  1  1  1  1    0  0  0  0  0  0  0  0    1  0  0  0  0  1  1  1    0  0  0  0  0  1  0  1
//31 30 29 28 27 26 25 24   23 22 21 20 19 18 17 16   15 14 13 12 11 10 9  8    7  6  5  4  3  2  1  0

public class ImageConveterImplementation implements ImageConverter {
//    private String alpha;

    @Override
    public Color[][] convertToColor(int[][] image) {
        //формула: x = a*255/127  (a - как в int[][], x - для записи в Color[][])
        System.out.println("toColor:");
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
//                System.out.println(image[i][j] + " " + imageColor[i][j].toString());
            }
//            System.out.println();
        }
        return imageColor;
    }

    ;

    @Override
    public int[][] convertToRgb(Color[][] image) {
        //формула: x = a*127/255  (a - как в Color[][], x - для записи в int[][])
        int[][] imageInt = new int[image.length][image[0].length];
        System.out.println("toRGB:");
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
                } finally {
//                    System.out.println(image[i][j].toString() + "  " + imageInt[i][j]);
                }
          }
//            System.out.println();
        }
        return imageInt;
    }

    ;
}
