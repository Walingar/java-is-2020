package model;

import java.awt.*;

public class ImageData {

    private final int height;

    private final int width;

    private final Color[][] matrix;

    public ImageData(Color[][] imageMatrix) {
        this.height = imageMatrix.length;
        this.width = imageMatrix[0].length;
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
