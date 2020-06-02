package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreadsCount;
    private double[][] a;
    private double[][] b;
    private double[][] result;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        var aNumRows = a.length;
        var bNumColumns = b[0].length;

        double[][] result = new double[aNumRows][bNumColumns];

        var maxNumThreads = Math.min(aNumRows, maxThreadsCount);

        ArrayList<Thread> threads = new ArrayList<>();
        var startRowIndex = 0;
        // https://stackoverflow.com/a/21830188
        var numRowsDelta = (aNumRows + maxNumThreads - 1) / maxNumThreads;

        for (var i = 0; i < maxNumThreads; i++) {
            var endRowIndex = startRowIndex + numRowsDelta - 1;
            var thread = new Thread(new ConcurrentWorker(a, b, result, startRowIndex, endRowIndex));
            threads.add(thread);
            thread.start();
            startRowIndex = endRowIndex + 1;
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

    private class ConcurrentWorker implements Runnable {
        private double[][] a;
        private double[][] b;
        private double[][] result;
        private int startRowIndex;
        private int endRowIndex;

        public ConcurrentWorker(double[][] a, double[][] b, double[][] result, int startRowIndex, int endRowIndex) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.startRowIndex = startRowIndex;
            this.endRowIndex = endRowIndex;
        }

        @Override
        public void run() {
            var aNumColumns = a[0].length;
            var bNumColumns = b[0].length;

            for (int i = startRowIndex; i <= endRowIndex; i++) {
                for (var j = 0; j < bNumColumns; j++) {
                    for (var k = 0; k < aNumColumns; k++) {
                        result[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
    }

}
