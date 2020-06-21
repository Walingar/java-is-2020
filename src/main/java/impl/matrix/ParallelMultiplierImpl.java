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
        int rowCount = a.length;
        int columnCount = b[0].length;
        double[][] resultMatrix = new double[rowCount][columnCount];

        ArrayList<Thread> threads = new ArrayList<>();

        int startRowNumber = 0;
        int rowOffset = (rowCount + maxThreadsCount - 1) / maxThreadsCount;

        for (int i = 0; i < maxThreadsCount; i++) {
            int endRowNumber = startRowNumber + rowOffset - 1;
            Thread thread = new Thread(new Task(a, b, resultMatrix, startRowNumber, endRowNumber));
            threads.add(thread);
            thread.start();
            startRowNumber = endRowNumber + 1;
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return resultMatrix;
    }

    private class Task implements Runnable {
        private final int startRowNumber;
        private final int endRowNumber;
        private final int bColumnNumber;
        private final int aColumnNumber;
        private final double[][] aMatrix;
        private final double[][] bMatrix;
        private final double[][] resultMatrix;

        public Task(double[][] aMatrix, double[][] bMatrix, double[][] resultMatrix, int startRowNumber, int endRowNumber) {
            this.aMatrix = aMatrix;
            this.bMatrix = bMatrix;
            this.resultMatrix = resultMatrix;
            this.startRowNumber = startRowNumber;
            this.endRowNumber = endRowNumber;
            this.bColumnNumber = bMatrix[0].length;
            this.aColumnNumber = aMatrix[0].length;
        }

        @Override
        public void run() {
            for (int i = startRowNumber; i <= endRowNumber; i++) {
                for (var j = 0; j < bColumnNumber; j++) {
                    for (var k = 0; k < aColumnNumber; k++) {
                        resultMatrix[i][j] += aMatrix[i][k] * bMatrix[k][j];
                    }
                }
            }
        }
    }
}
