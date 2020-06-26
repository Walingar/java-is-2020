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
    public double[][] mul(double[][] firstMatrix, double[][] secondMatrix) {
        int firstMatrixHeight = firstMatrix.length;
        int secondMatrixWidth = secondMatrix[0].length;

        int resultMatrixHeight = firstMatrixHeight;
        int resultMatrixWidth = secondMatrixWidth;

        double[][] resultMatrix = new double[resultMatrixHeight][resultMatrixWidth];

        // double cycle -> single cycle
        int iterationsCount = resultMatrixWidth * resultMatrixHeight;
        int partitionSize = iterationsCount / maxThreadsCount;

        partitionSize = partitionSize == 0 ? 1 : partitionSize;

        List<Thread> threads = new ArrayList<>();

        for (int index = 0; index < maxThreadsCount; index++) {
            int start = index * partitionSize;
            int end = (index + 1) * partitionSize;

            // Check if we have too much threads
            if (start > iterationsCount) {
                break;
            }

            // Check boundary case
            if (index == maxThreadsCount - 1) {
                end = iterationsCount;
            }

            var thread = new Thread(new Multiplicator(firstMatrix, secondMatrix, resultMatrix, start, end));
            threads.add(thread);
            thread.start();
        }

        // Wait for threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        return resultMatrix;
    }

    private static final class Multiplicator implements Runnable {
        private final double[][] firstMatrix;
        private final double[][] secondMatrix;

        private final double[][] resultMatrix;

        private final int firstMatrixWidth;
        private final int secondMatrixWidth;

        private final int start;
        private final int end;

        private Multiplicator(double[][] firstMatrix, double[][] secondMatrix, double[][] result, int start, int end) {
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;

            this.resultMatrix = result;

            this.start = start;
            this.end = end;

            firstMatrixWidth = firstMatrix[0].length;
            secondMatrixWidth = secondMatrix[0].length;
        }

        @Override
        public void run() {
            for (int index = start; index < end; index++) {
                int row = index / secondMatrixWidth;
                int column = index % secondMatrixWidth;

                resultMatrix[row][column] = calculateElement(row, column);
            }
        }

        private double calculateElement(int row, int column) {
            double elementValue = 0;

            for (int index = 0; index < firstMatrixWidth; index++) {
                elementValue += firstMatrix[row][index] * secondMatrix[index][column];
            }

            return elementValue;
        }
    }
}
