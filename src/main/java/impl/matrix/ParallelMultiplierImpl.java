package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;
import java.util.List;

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
        var numberOfResultElements = aRowNumber * bColumnNumber;

        List<Thread> threadList = new ArrayList<>();

        int start = 0;

        for (int i = 0; i < maxThreads; i++) {
            int currentStep = numberOfResultElements / maxThreads + (numberOfResultElements % maxThreads > i ? 1 : 0);
            var end = start + currentStep - 1;
            var thread = new Thread(new Multiplier(start, end, a, b, result));
            threadList.add(thread);
            thread.start();
            start = end + 1;
        }

        for (var thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

    private static class Multiplier implements Runnable {
        private final int start;
        private final int end;

        private final double[][] a;
        private final double[][] b;
        private final double[][] result;

        public Multiplier(int start, int end, double[][] a, double[][] b, double[][] result) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            int columnNumber = b[0].length;
            for (int i = start; i <= end; i++) {
                int row = i / columnNumber;
                int column = i % columnNumber;
                for (int k = 0; k < b.length; k++) {
                    result[row][column] += a[row][k] * b[k][column];
                }
            }
        }
    }
}
