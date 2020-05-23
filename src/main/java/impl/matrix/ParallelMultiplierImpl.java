package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;
import java.util.List;

public final class ParallelMultiplierImpl implements ParallelMultiplier {
    private final int maxThreadsCount;

    public ParallelMultiplierImpl(final int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(final double[][] a, final double[][] b) {
        int resultRows = a.length;
        int resultColumns = b[0].length;
        double[][] result = new double[resultRows][resultColumns];
        List<Thread> threads = new ArrayList<>(maxThreadsCount);
        int numberOfResultElements = resultColumns * resultRows;
        int commonJobSize = numberOfResultElements / maxThreadsCount;
        if (commonJobSize * maxThreadsCount < numberOfResultElements) {
            commonJobSize++;
        }
        int jobOffset = 0;
        for (int i = 0; i < maxThreadsCount; i++) {
            int currentJobSize = Math.min(commonJobSize, numberOfResultElements - jobOffset);
            if (currentJobSize <= 0) {
                break;
            }
            Thread thread = new Thread(new PartialMultiplier(result, a, b, jobOffset, currentJobSize));
            threads.add(thread);
            thread.start();
            jobOffset += currentJobSize;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

    private static final class PartialMultiplier implements Runnable {
        private final double[][] result;
        private final double[][] a;
        private final double[][] b;
        private final int offset;
        private final int partSize;

        public PartialMultiplier(final double[][] result, final double[][] a, final double[][] b, final int offset, final int partSize) {
            this.result = result;
            this.a = a;
            this.b = b;
            this.offset = offset;
            this.partSize = partSize;
        }

        @Override
        public void run() {
            int resultColumns = b[0].length;
            int n = b.length;
            for (int elementIdx = offset; elementIdx < offset + partSize; ++elementIdx) {
                int row = elementIdx / resultColumns;
                int column = elementIdx % resultColumns;
                for (int i = 0; i < n; i++) {
                    result[row][column] += a[row][i] * b[i][column];
                }
            }
        }
    }
}
