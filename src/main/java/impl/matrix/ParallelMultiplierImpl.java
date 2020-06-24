package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreadCount;

    ParallelMultiplierImpl(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {

        int aRowsCnt = a.length;
        int bColumnsCnt = b[0].length;
        double[][] result = new double[aRowsCnt][bColumnsCnt];
        int operationsCnt = aRowsCnt * bColumnsCnt;
        int partSize = operationsCnt / maxThreadCount;
        Thread[] threads = new Thread[maxThreadCount - 1];

        for (int i = 0; i < threads.length; i++) {
            int partBegin = i * partSize;
            int partEnd = partBegin + partSize;
            threads[i] = new Thread(() -> calculatePart(a, b, result, partBegin, partEnd));
            threads[i].start();
        }

        calculatePart(a, b, result, threads.length * partSize, operationsCnt);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }

        return result;
    }

    private void calculatePart(double[][] a, double[][] b, double[][] res, int partBegin, int partEnd) {

        int rowSize = res[0].length;
        int commonSideLength = b.length;
        for (int element = partBegin; element < partEnd; element++) {
            int i = element / rowSize;
            int j = element % rowSize;
            for (int k = 0; k < commonSideLength; k++) {
                res[i][j] += a[i][k] * b[k][j];
            }
        }

    }

}
