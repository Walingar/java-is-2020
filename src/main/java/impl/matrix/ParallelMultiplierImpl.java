package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;

public class ParallelMultiplierImpl implements ParallelMultiplier {
    private final int maxThreadsCount;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        var leftMatrix = new Matrix(a);
        var rightMatrix = new Matrix(b);
        if (leftMatrix.getWidth() != rightMatrix.getHeight()) {
            throw new IllegalArgumentException(
                    String.format("Can not multiply matrices of size %dx%d and %dx%d", leftMatrix.getHeight(),
                                  leftMatrix.getWidth(), rightMatrix.getHeight(), rightMatrix.getWidth()));
        }
        var result = new Matrix(leftMatrix.getHeight(), rightMatrix.getWidth());

        var threads = new ArrayList<Thread>();
        var allJobsSize = leftMatrix.getHeight() * rightMatrix.getWidth();
        int oneJobSize = allJobsSize / maxThreadsCount + 1;
        for (int threadIndex = 0; threadIndex < maxThreadsCount; ++threadIndex) {
            long startOffset = oneJobSize * threadIndex;
            long endOffset = Math.min(oneJobSize * (threadIndex + 1), allJobsSize);
            threads.add(new Thread(new MultiplierJob(leftMatrix, rightMatrix, result, startOffset, endOffset)));
        }

        threads.forEach(Thread::start);
        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        return result.getMatrix();
    }

    private static class MultiplierJob implements Runnable {
        private final Matrix left;
        private final Matrix right;
        private final Matrix result;
        private final long startOffset;
        private final long endOffset;

        private MultiplierJob(Matrix left, Matrix right, Matrix result, long startOffset, long endOffset) {
            this.left = left;
            this.right = right;
            this.result = result;
            this.startOffset = startOffset;
            this.endOffset = endOffset;
        }

        @Override
        public void run() {
            var rowSize = right.getWidth();
            for (long offset = startOffset; offset < endOffset; ++offset) {
                int i = (int) (offset / rowSize);
                int j = (int) (offset % rowSize);
                long sum = 0;
                for (int k = 0; k < right.getHeight(); ++k) {
                    sum += left.get(i, k) * right.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
    }
}
