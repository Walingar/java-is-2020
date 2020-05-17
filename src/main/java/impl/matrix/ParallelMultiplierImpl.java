package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreadCount;

    ParallelMultiplierImpl(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {

        double[][] result = new double[a.length][b[0].length];
        int operationsCnt = a.length * b[0].length;
        int partSize = operationsCnt / maxThreadCount;
        Thread[] threads = new Thread[maxThreadCount - 1];

        for (int i = 0; i < threads.length; i++) {
            int partBegin = i * partSize;
            int partEnd = partBegin + partSize;
            threads[i] = new Thread(() -> calulatePart(a, b, result, partBegin, partEnd));
            threads[i].start();
        }

        calulatePart(a, b, result, threads.length * partSize, operationsCnt);

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignored) {
            }
        }

        return result;
    }

    private void calulatePart(double[][] a, double[][] b, double[][] res, int partBegin, int partEnd) {

        int rowSize = res[0].length;
        for (int element = partBegin; element < partEnd; element++) {
            int i = element / rowSize;
            int j = element % rowSize;
            for (int k = 0; k < b.length; k++) {
                res[i][j] += a[i][k] * b[k][j];
            }
        }

    }

}
