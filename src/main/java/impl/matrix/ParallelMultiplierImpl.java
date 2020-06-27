package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;
import java.util.List;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreadsCount;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        var aNumRows = a.length;
        var bNumColumns = b[0].length;

        double[][] result = new double[aNumRows][bNumColumns];

        var maxNumThreads = Math.min(aNumRows * bNumColumns, maxThreadsCount);

        List<Thread> threads = new ArrayList<>();
        var startFlatIndex = 0;
        // https://stackoverflow.com/a/21830188
        var flatIndexDelta = (aNumRows * bNumColumns + maxNumThreads - 1) / maxNumThreads;

        for (var i = 0; i < maxNumThreads; i++) {
            var endFlatIndex = startFlatIndex + flatIndexDelta - 1;
            var thread = new Thread(new ConcurrentWorker(a, b, result, startFlatIndex, endFlatIndex));
            threads.add(thread);
            thread.start();
            startFlatIndex = endFlatIndex + 1;
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Catched error in thread.");
            }
        }
        return result;
    }



    private static class ConcurrentWorker implements Runnable {
        private final double[][] a;
        private final double[][] b;
        private final double[][] result;
        private final int startFlatIndex;
        private final int endFlatIndex;

        public ConcurrentWorker(double[][] a, double[][] b, double[][] result, int startFlatIndex, int endFlatIndex) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.startFlatIndex = startFlatIndex;
            this.endFlatIndex = endFlatIndex;
        }

        @Override
        public void run() {
            var bNumRows = b.length;
            var bNumColumns = b[0].length;

            for (var i = startFlatIndex; i <= endFlatIndex; i++) {
                var row = i / bNumColumns;
                var column = i % bNumColumns;
                result[row][column] = 0;
                for (var k = 0; k < bNumRows; k++) {
                    result[row][column] += a[row][k] * b[k][column];
                }
            }
        }
    }

}
