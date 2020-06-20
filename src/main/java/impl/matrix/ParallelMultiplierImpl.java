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
        int size = heightA / maxThreadsCount;
        int widthB = 0;
        if (b.length != 0) {
            widthB = b[0].length;
        }
        double[][] resultMatrix = new double[heightA][widthB];
        for (int count = 0; count < maxThreadsCount; count++) {
            int start = count * size;
            int end = (count + 1) * size;
            if (count == maxThreadsCount - 1) {
                end = (count + 1) * size + heightA % maxThreadsCount;
            }
            Thread thread = new Thread(new RunMultiplyThread(a, b, widthB, resultMatrix, start, end));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return resultMatrix;
    }

    private static class RunMultiplyThread implements Runnable {
        double[][] a;
        double[][] b;
        int widthB;
        double[][] resultMatrix;
        int start;
        int end;

        public RunMultiplyThread(double[][] a, double[][] b, int widthB, double[][] resultMatrix, int start, int end) {
            this.a = a;
            this.b = b;
            this.widthB = widthB;
            this.resultMatrix = resultMatrix;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < widthB; j++) {
                    for (int k = 0; k < b.length; k++) {
                        resultMatrix[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
    }
}
