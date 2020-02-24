package impl.image;

import api.image.ImageConverter;

import java.awt.*;

//        alpha                       red                      green                     blue
//0  1  2  3  4  5  6  7    8  9  10 11 12 13 14 15   16 17 18 19 20 21 22 23   24 25 26 27 28 29 30 31
//1  1  1  1  1  1  1  1    0  0  0  0  0  0  0  0    1  0  0  0  0  1  1  1    0  0  0  0  0  1  0  1
//31 30 29 28 27 26 25 24   23 22 21 20 19 18 17 16   15 14 13 12 11 10 9  8    7  6  5  4  3  2  1  0

public class ImageConveterImplementation implements ImageConverter {
    private String alpha;

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
//                System.out.println(tempStr + ":");
                String alpha = tempStr.substring(0, 8);
                String red = tempStr.substring(8, 16);
                String green = tempStr.substring(16, 24);
                String blue = tempStr.substring(24);
//                System.out.println("red:" + red + "; green:" + green + "; blue:" + blue + "; alpha:" + alpha);
                imageColor[i][j] = new Color(Integer.parseInt(red, 2), Integer.parseInt(green, 2), Integer.parseInt(blue, 2), Integer.parseInt(alpha, 2));
//                System.out.println(imageColor[i][j].toString());
            }
            System.out.println();
        }
        return imageColor;
    }

    ;

    public int[][] convertToRgb(Color[][] image) {
        //формула: x = a*127/255  (a - как в Color[][], x - для записи в int[][])
        int[][] imageInt = new int[10][10];
        System.out.println("toRGB:");
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                System.out.print(image[i][j].toString() + "  ");
            }
            System.out.println();
        }
        return null;
    }

    ;
}
