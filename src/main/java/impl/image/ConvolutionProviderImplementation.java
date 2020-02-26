package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConvolutionProviderImplementation implements ConvolutionProvider {

    @Override
    public Color[][] apply(Color[][] image, double[][] kernel) {
        Color[][] bluredImage = new Color[image.length][image[0].length];
        int size = kernel.length;
        int step = (int) Math.floor(size / 2);
        boolean edge;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                // bluring
//                edge = false;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int m1 = -step, m2 = size - 1; m1 <= i + step && m2 >= 0; m1++, m2--) {
                    for (int n1 = -step, n2 = size - 1; n1 <= j + step && n2 >= 0; n1++, n2--) {
//
//                        if (i + m1 < 0 || i + m1 > image.length || i + n1 < 0 || i + n1 > image[0].length) {
//                            red = 0;
//                            green = 0;
//                            blue = 0;
//                            edge = true;
//                            break;
//                        }

                        try{
                            red += (int) image[i + m1][j + n1].getRed() * kernel[m2][n2];
                        }catch (ArrayIndexOutOfBoundsException e){
                            red = Math.max(0, red);
                        }

                        try{
                            green += (int) image[i + m1][j + n1].getGreen() * kernel[m2][n2];
                        }catch (ArrayIndexOutOfBoundsException e){
                            green = Math.max(0, green);
                        }

                        try{
                            blue += (int) image[i + m1][j + n1].getBlue() * kernel[m2][n2];
                        }catch (ArrayIndexOutOfBoundsException e){
                            blue = Math.max(0, blue);
                        }

//                        try{
//                            red += (int) image[i + m1][j + n1].getRed() * kernel[m2][n2];
//                            green += (int) image[i + m1][j + n1].getGreen() * kernel[m2][n2];
//                            blue += (int) image[i + m1][j + n1].getBlue() * kernel[m2][n2];
//                        }catch(ArrayIndexOutOfBoundsException e){
//                            red = 0;
//                            green = 0;
//                            blue = 0;
//                            edge = true;
//                            break;
//                        }


                    }
//                    if (edge) {
//                        break;
//                    }
                }

                bluredImage[i][j] = new Color(red, green, blue, 255);


//                свертка
//                for (int m = 0; m < kernel.length; m++) {
//                    for (int n = 0; n < kernel.length; n++) {
//                        int red = Math.round((float) (kernel[kernel.length - m][kernel.length - n] * image[m + i][n + j].getRed()));
//                        int green = Math.round((float) (kernel[kernel.length - m][kernel.length - n] * image[m + i][n + j].getGreen()));
//                        int blue = Math.round((float) (kernel[kernel.length - m][kernel.length - n] * image[m + i][n + j].getBlue()));
//
//                    }
//                }
            }
        }

        // for test
        ImageUtil.writeOutputImage("myImage.png", bluredImage);

        try {
            FileWriter writer1 = new FileWriter("originImage.txt", false);
            FileWriter writer2 = new FileWriter("bluredImage.txt", false);
            FileWriter writer3 = new FileWriter("rightImage.txt", false);

            image = ImageUtil.readImage(new File("resources/image/origin/" + "pic1.png"));
            writer1.append("Origin image"+"\n\n");
            writer1.append("\nRed:"+'\n');
            for (int i=0; i<image.length; i++){
                for (int j=0; j<image[0].length; j++){
                    writer1.append(image[i][j].getRed()+"  ");
                }
                writer1.append('\n');
            }
            writer1.append("\nGreen:"+'\n');
            for (int i=0; i<image.length; i++){
                for (int j=0; j<image[0].length; j++){
                    writer1.append(image[i][j].getGreen()+"  ");
                }
                writer1.append('\n');
            }
            writer1.append("\nBlue:"+'\n');
            for (int i=0; i<image.length; i++){
                for (int j=0; j<image[0].length; j++){
                    writer1.append(image[i][j].getBlue()+"  ");
                }
                writer1.append('\n');
            }

//            writer2.append("Blured image: "+'\n');
//            for (int i=0; i<bluredImage.length; i++){
//                for (int j=0; j<bluredImage[0].length; j++){
//                    writer2.append(bluredImage[i][j].toString()+"  ");
//                }
//                writer2.append('\n');
//            }
            writer2.append("Blured image"+"\n\n");
            writer2.append("\nRed:"+'\n');
            for (int i=0; i<bluredImage.length; i++){
                for (int j=0; j<bluredImage[0].length; j++){
                    writer2.append(bluredImage[i][j].getRed()+"  ");
                }
                writer2.append('\n');
            }
            writer2.append("\nGreen:"+'\n');
            for (int i=0; i<bluredImage.length; i++){
                for (int j=0; j<bluredImage[0].length; j++){
                    writer2.append(bluredImage[i][j].getGreen()+"  ");
                }
                writer2.append('\n');
            }
            writer2.append("\nBlue:"+'\n');
            for (int i=0; i<bluredImage.length; i++){
                for (int j=0; j<bluredImage[0].length; j++){
                    writer2.append(bluredImage[i][j].getBlue()+"  ");
                }
                writer2.append('\n');
            }

            Color[][] rightImage = ImageUtil.readImage(new File("resources/image/blur/" + "pic1.png"));
            writer3.append("Right image"+"\n\n");
            writer3.append("\nRed:"+'\n');
            for (int i=0; i<rightImage.length; i++){
                for (int j=0; j<rightImage[0].length; j++){
                    writer3.append(rightImage[i][j].getRed()+"  ");
                }
                writer3.append('\n');
            }
            writer3.append("\nGreen:"+'\n');
            for (int i=0; i<rightImage.length; i++){
                for (int j=0; j<rightImage[0].length; j++){
                    writer3.append(rightImage[i][j].getGreen()+"  ");
                }
                writer3.append('\n');
            }
            writer3.append("\nBlue:"+'\n');
            for (int i=0; i<rightImage.length; i++){
                for (int j=0; j<rightImage[0].length; j++){
                    writer3.append(rightImage[i][j].getBlue()+"  ");
                }
                writer3.append('\n');
            }

            writer1.flush();
            writer2.flush();
            writer3.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("Origin image:");
//        for (int i=0; i<image.length; i++){
//            for (int j=0; j<image[0].length; j++){
//                System.out.println(image[i][j].toString()+"   ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println("Blured image:");
//        for (int i=0; i<bluredImage.length; i++){
//            for (int j=0; j<bluredImage[0].length; j++){
//                System.out.println(bluredImage[i][j]+"   ");
//            }
//            System.out.println();
//        }
        return bluredImage; // вернуть bluredImage!!!
    }
}
