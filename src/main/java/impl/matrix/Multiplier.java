package impl.matrix;

public class Multiplier implements Runnable {
    private final int start;
    private final int end;
    private final double[][] a;
    private final double[][] b;
    private final double[][] result;

    public Multiplier(int start, int end, double[][] a, double[][] b, double[][] result) {
        this.start = start;
        this.end = end;
        this.a = a;
        this.b = b;
        this.result = result;
    }

    @Override
    public void run() {
        int bRows = b.length;
        int bColumns = bRows == 0 ? 0 : b[0].length;
        for (long i = start; i < end * bColumns; i++) {
            int row = (int) (i / bColumns);
            int column = (int) (i % end);
            for (int inner = 0; inner < bRows; inner++) {
                result[row][column] += a[row][inner] * b[inner][column];
            }
        }
    }
}
