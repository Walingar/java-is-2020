package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;
import java.util.List;

public class ParallelMultiplierImpl implements ParallelMultiplier {
    private final int maxThreadsCount;
    private static double[][] result;

    ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        if (b.length != a[0].length) {
            return new double[0][0];
        }

        var aHeight = a.length;
        var bWidth = b[0].length;
        var numberOfThreads = Math.min(maxThreadsCount, aHeight * bWidth);
        int[] offsets = calculateOffsets(aHeight, bWidth, numberOfThreads);
        result = new double[aHeight][bWidth];

        try {
            List<Worker> workers = new ArrayList<>(numberOfThreads);
            for (var i = 0; i < numberOfThreads; i++) {
                Worker worker = new Worker(a, b, offsets[i], offsets[i + 1] - 1);
                worker.start();
                workers.add(worker);
            }
            for (Worker worker : workers) {
                worker.join();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    private int[] calculateOffsets(int aHeight, int bWidth, int numberOfThreads) {
        int[] iterationsPerWorker = new int[numberOfThreads];
        for (var i = 0; i < numberOfThreads; i++) {
            iterationsPerWorker[i] = aHeight * bWidth / numberOfThreads;
        }
        for (var i = 0; i <= aHeight * bWidth - aHeight * bWidth / numberOfThreads * numberOfThreads; i++) {
            iterationsPerWorker[i]++;
        }
        int[] offsets = new int[numberOfThreads + 1];
        offsets[0] = 0;
        for (var i = 1; i < numberOfThreads; i++) {
            offsets[i] = offsets[i - 1] + iterationsPerWorker[i];
        }
        offsets[numberOfThreads] = aHeight * bWidth;
        return offsets;
    }

    private static class Worker extends Thread {
        private double[][] a;
        private double[][] b;
        private int start;
        private int end;

        Worker(double[][] a, double[][] b, int start, int end) {
            this.a = a;
            this.b = b;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            var aHeight = a.length;
            var aWidth = a[0].length;
            for (var i = start; i <= end; i++) {
                var row = i / aHeight;
                var column = i % aHeight;
                result[row][column] = 0;
                for (var k = 0; k < aWidth; k++) {
                    result[row][column] += a[row][k] * b[k][column];
                }
            }
        }
    }
}
