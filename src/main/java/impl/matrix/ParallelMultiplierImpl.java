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
        int rowCount = a.length;
        int columnCount = b[0].length;
        double[][] resultMatrix = new double[rowCount][columnCount];

        int elementCount = rowCount * columnCount;

        List<Thread> threads = new ArrayList<>();

        int startElementNumber = 0;

        for (int i = 0; i < maxThreadsCount; i++) {
            int offset = elementCount / maxThreadsCount + (elementCount % maxThreadsCount > i ? 1 : 0);
            int endElementNumber = startElementNumber + offset - 1;
            Thread thread = new Thread(new Task(a, b, resultMatrix, startElementNumber, endElementNumber));
            threads.add(thread);
            thread.start();
            startElementNumber = endElementNumber + 1;
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread Error");
            }
        }
        return resultMatrix;
    }

    private static class Task implements Runnable {
        private final int start;
        private final int end;
        private final double[][] aMatrix;
        private final double[][] bMatrix;
        private final double[][] resultMatrix;

        public Task(double[][] aMatrix, double[][] bMatrix, double[][] resultMatrix, int start, int end) {
            this.aMatrix = aMatrix;
            this.bMatrix = bMatrix;
            this.resultMatrix = resultMatrix;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            int rowLength = resultMatrix[0].length;
            for (int i = start; i <= end; i++) {
                int rowNumber = i / rowLength;
                int columnNumber = i % rowLength;
                for (var j = 0; j < bMatrix.length; j++) {
                    resultMatrix[rowNumber][columnNumber] += aMatrix[rowNumber][j] * bMatrix[j][columnNumber];
                }
            }
        }
    }
}