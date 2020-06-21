package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {
    private final int maxThreadCount;

    ParallelMultiplierImpl(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        Thread[] threads = new Thread[maxThreadCount];
        double[][] result = new double[a.length][b[0].length];
        int iterations = a.length * b[0].length;
        int currentIter = 0;

        for (int i = 0; i < threads.length; i++) {
            int startIter = currentIter;
            int threadPart = iterations / maxThreadCount + (iterations % maxThreadCount > i ? 1 : 0);
            threads[i] = new Thread(() -> multiply(a, b, result, startIter, threadPart));
            currentIter += threadPart;
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
        return result;
    }

    private void multiply(double[][] a, double[][] b, double[][] res, int startIter, int threadPart) {
        int rowSize = res[0].length;

        for (int j = startIter; j < startIter + threadPart; j++) {
            int row = j / rowSize;
            int column = j % rowSize;

            for (int i = 0; i < a[0].length; i++) {
                res[row][column] += a[row][i] * b[i][column];
            }
        }
    }
}