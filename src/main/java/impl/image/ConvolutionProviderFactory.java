package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;

public class ConvolutionProviderFactory {
    public static ConvolutionProvider getInstance() {

        ConvolutionProvider convolution = (image, kernel) -> {
                Color[][] imageNew = new Color[image.length][image[0].length];
                int middleIndKern = kernel.length/2;

                for (int imageRow = 0; imageRow<image.length; imageRow++){
                    int lengthColumns = image[imageRow].length;
                    for (int imageColumn = 0; imageColumn<lengthColumns; imageColumn++){
                        int startRowImg = imageRow -middleIndKern;
                        int startColumnImg = imageColumn-middleIndKern;
                        int startRowKern = kernel.length-1;
                        int startColumnKern = kernel[0].length-1;
                        int endRowKern = 0;
                        int endColumnKern = 0;
                        if (imageRow < middleIndKern) {
                            int dist = middleIndKern-imageRow;
                            startRowImg += dist;
                            startRowKern -= dist;
                        }
                        if (imageColumn < middleIndKern){
                            int dist = middleIndKern-imageColumn;
                            startColumnImg += dist;
                            startColumnKern -= dist;
                        }
                        if(image.length-imageRow-1<middleIndKern){
                            endRowKern = kernel.length - middleIndKern - 1;
                        }
                        if (lengthColumns - imageColumn-1<middleIndKern){
                            endColumnKern = kernel[0].length - middleIndKern -1;
                        }
                        int sumBlue = 0;
                        int sumGreen = 0;
                        int sumRed = 0;
                        for (int kernelRow = startRowKern; kernelRow >=endRowKern; kernelRow--) {
                            int currentColumnImg = startColumnImg;
                            for (int kernelCol = startColumnKern; kernelCol>=endColumnKern; kernelCol--){
                                sumBlue += kernel[kernelRow][kernelCol]*image[startRowImg][currentColumnImg].getBlue();
                                sumGreen += kernel[kernelRow][kernelCol]*image[startRowImg][currentColumnImg].getGreen();
                                sumRed += kernel[kernelRow][kernelCol]*image[startRowImg][currentColumnImg].getRed();
                                currentColumnImg++;
                            }
                            startRowImg++;
                        }
                        imageNew[imageRow][imageColumn] = new Color(Math.min(Math.max(0, sumRed), 255),
                                Math.min(Math.max(0, sumGreen), 255),
                                Math.min(Math.max(0, sumBlue), 255));
                    }
                }
                return imageNew;
        };

        return convolution;
    }
}