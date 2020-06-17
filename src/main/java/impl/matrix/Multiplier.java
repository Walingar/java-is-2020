package impl.matrix;

public class Multiplier implements Runnable {
    int start, end;
    int bColumns, bRows;
    double[][] a, b, result;

    public Multiplier(int start, int end, int bColumns, int bRows, double[][] a, double[][] b, double[][] result) {
        this.start = start;
        this.end = end;
        this.bColumns = bColumns;
        this.bRows = bRows;
        this.a = a;
        this.b = b;
        this.result = result;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int inner = 0; inner < bRows; inner++) {
                    result[i][j] += a[i][inner] * b[inner][j];
                }
            }
        }
    }
}
