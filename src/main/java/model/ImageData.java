package model;

import java.awt.*;

public class ImageData {

    private int height;

    private int width;

    private Color[][] matrix;

    public ImageData(Color[][] imageMatrix, int imageHeight , int imageWidth) {
        this.height = imageHeight;
        this.width = imageWidth;
        this.matrix = imageMatrix;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Color[][] getMatrix(){
        return matrix;
    }

}
