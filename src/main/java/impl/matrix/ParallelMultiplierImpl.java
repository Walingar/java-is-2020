package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;
import java.util.List;
// This Algorithm of Multiplying is not efficient because of the O(n^3) complexity i tried to implement it using
// FFT (Fast Fourier Transform ) to multiply two polynomials in O(nlog) but i faced a lot of problems and the implementation was really huge so
// i cancelled it and returned to here
// Strassen divide and conquer algorithm doing better than this algorithm also Theoretically But the Cost of communication and stack growth is quite large making the improvement
// unclear or maybe i had a bad implementation.

public class ParallelMultiplierImpl implements ParallelMultiplier {
    private final int maxThreadsCount;
    private static double[][] res;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        int aWidth = a.length;
        int aHeight = a[0].length;
        int bWidth = b.length;
        int bHeight = b[0].length;
        int usedThreads = Math.min(maxThreadsCount, aHeight * bWidth);
        res = new double[aWidth][bHeight];
        if (aHeight != bWidth) {
            throw new IllegalArgumentException("To Multiply a Matrix of Size NxM with Another of Size UxV Then M = U Should Holds");
        }
        List<Thread> workers = distributeJob(aWidth, bHeight, usedThreads, a, b);
        for (var worker : workers) {
            worker.start();
        }
        for (var worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private List<Thread> distributeJob(int aWidth, int bHeight, int usedThreads, double[][] a, double[][] b) {
        int totalIterations = aWidth * bHeight;
        int iterationPerWorker = totalIterations / usedThreads;
        int remainIterations = totalIterations % usedThreads;
        List<Thread> workers = new ArrayList<>();
        int curBlock = 0;
        for (int threadID = 0; threadID < usedThreads; threadID++) {
            int blockEnd = curBlock + iterationPerWorker;
            if (remainIterations > 0) {
                remainIterations--;
                blockEnd++;
            }
            Thread worker = new Thread(new Worker(a, b, curBlock, blockEnd));
            workers.add(worker);
            curBlock = blockEnd;
        }
        return workers;
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
            int aHeight = a[0].length;
            int bHeight = b[0].length;
            for (var i = start; i < end; i++) {
                var row = i / bHeight;
                var column = i % bHeight;
                res[row][column] = 0;
                for (var k = 0; k < aHeight; k++) {
                    res[row][column] += a[row][k] * b[k][column];
                }
            }
        }
    }
}
