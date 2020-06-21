package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreads;

    public ParallelMultiplierImpl(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        int aRowNumber = a.length;
        int bColumnNumber = b[0].length;
        double[][] result = new double[aRowNumber][bColumnNumber];

        ArrayList<Thread> threadList = new ArrayList<>();

        int startRow = 0;
        int rowStep = (aRowNumber + maxThreads - 1) / maxThreads;

        for (int i = 0; i < maxThreads; i++) {
            var endRow = startRow + rowStep - 1;
            var thread = new Thread(new Multiplier(startRow, endRow, a, b, result));
            threadList.add(thread);
            thread.start();
            startRow = endRow + 1;
        }

        for (var thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread error: " + e);
            }
        }
        return result;
    }

    private class Multiplier implements Runnable {
        private final int start;
        private final int end;
        private final int bColumnNumber;
        private final int aColumnNumber;

        private final double[][] a;
        private final double[][] b;
        private final double[][] result;

        public Multiplier(int start, int end, double[][] a, double[][] b, double[][] result) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.start = start;
            this.end = end;
            this.bColumnNumber = b[0].length;
            this.aColumnNumber = a[0].length;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i++) {
                for (var j = 0; j < bColumnNumber; j++) {
                    for (var k = 0; k < aColumnNumber; k++) {
                        result[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
    }
}
