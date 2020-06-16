package impl.matrix;

public class RunnableImpl implements Runnable {
    double[][] a;
    double[][] b;
    double[][] c;
    int startI;
    int finishI;

    RunnableImpl(double[][] a, double[][] b, double[][] c, int startI, int finishI) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.startI = startI;
        this.finishI = finishI;
    }

    @Override
    public void run() {
        for (int i = startI; i < finishI; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }
}
