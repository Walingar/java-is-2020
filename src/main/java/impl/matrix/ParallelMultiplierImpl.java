package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {
    private final int maxThreadsCount;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        int heightA = a.length;
        int widthB = b[0].length;
        double[][] resultMatrix = new double[heightA][widthB];
        int size = heightA * widthB / maxThreadsCount;
        Thread[] threads = new Thread[maxThreadsCount];
        for (int count = 0; count < maxThreadsCount; count++) {
            int start = count * size;
            int end = (count + 1) * size;
            if (count == maxThreadsCount - 1) {
                end = (count + 1) * size + heightA * widthB % maxThreadsCount;
            }
            threads[count] = new Thread(new RunMultiplyThread(a, b, resultMatrix, start, end));
            threads[count].start();
        }
        try {
            for (int i = 0; i < maxThreadsCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resultMatrix;
    }

    private static final class RunMultiplyThread implements Runnable {
        double[][] a;
        double[][] b;
        double[][] resultMatrix;
        int start;
        int end;

        public RunMultiplyThread(double[][] a, double[][] b, double[][] resultMatrix, int start, int end) {
            this.a = a;
            this.b = b;
            this.resultMatrix = resultMatrix;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            int widthB = b[0].length;
            for (int i = start; i < end; i++) {
                int row = i / widthB;
                int column = i % widthB;
                for (int k = 0; k < b.length; k++) {
                    resultMatrix[row][column] += a[row][k] * b[k][column];
                }
            }
        }
    }
}
