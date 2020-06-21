package impl.matrix;

public class RunnableImpl implements Runnable {
    double[][] a;
    double[][] b;
    double[][] c;
    long startI;
    long finishI;

    RunnableImpl(double[][] a, double[][] b, double[][] c, long startI, long finishI) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.startI = startI;
        this.finishI = finishI;
    }

    @Override
    public void run() {

        int bRowSize = b[0].length;
        for (long i = startI; i < finishI; i++) {
            int column = (int) i % bRowSize;
            int row = (int) i / bRowSize;
            double sum = 0;
            for (int k = 0; k < a[0].length; k++) {
                sum += a[row][k] * b[k][column];
            }
            c[row][column] = sum;
        }

    }
}
