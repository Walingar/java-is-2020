package model;

public class KernelData {

    private final int height;

    private final int width;

    private final double[][] matrix;

    public KernelData(double[][] kernelMatrix) {
        height = kernelMatrix.length;
        width = kernelMatrix[0].length;
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
