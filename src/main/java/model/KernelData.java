package model;

public class KernelData {

    private int height;

    private int width;

    private double[][] matrix;

    public KernelData(double[][] kernelMatrix, int kernelHeight, int kernelWidth) {
        height = kernelHeight;
        width = kernelWidth;
        matrix = kernelMatrix;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(){
        return width;
    }

    public double[][] getMatrix(){
        return matrix;
    }
}
