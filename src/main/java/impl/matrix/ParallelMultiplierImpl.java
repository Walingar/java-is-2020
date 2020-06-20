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
        int totalOperations = a.length * b[0].length;
        int stepSize = totalOperations / maxThreadCount;
        Thread[] threads = new Thread[maxThreadCount - 1];

        for (int i = 0; i < threads.length; i++) {
            int start = i * stepSize;
            int end = start + stepSize;
            threads[i] = new Thread(() -> multiply(a, b, result, start, end));
            threads[i].start();
        }

        multiply(a, b, result, threads.length * stepSize, totalOperations);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
        return result;
    }

    private void multiply(double[][] a, double[][] b, double[][] res, int start, int end) {
        int rowSize = res[0].length;
        for (int i = start; i < end; i++) {
            int row = i / rowSize;
            int column = i % rowSize;
            for (int k = 0; k < b.length; k++) {
                res[row][column] += a[row][k] * b[k][column];
            }
        }
    }
}