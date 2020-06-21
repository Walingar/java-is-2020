package impl.matrix;

class Matrix {
    private final double[][] matrix;
    private final int height;
    private final int width;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    public Matrix(int height, int width) {
        this(new double[height][width]);
    }

    public double get(int row, int column) {
        return matrix[row][column];
    }

    public void set(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}