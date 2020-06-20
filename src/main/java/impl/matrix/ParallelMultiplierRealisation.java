package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierRealisation implements ParallelMultiplier {
    private int numberOfThreads;

    public ParallelMultiplierRealisation(int numberOfThreads){
        this.numberOfThreads = Math.max(numberOfThreads, 1);
    }


    @Override
    public double[][] mul(double[][] a, double[][] b) throws InterruptedException {
        int numberOfFinalMatrixRows = a.length;
        int numberOfFinalMatrixColumns = b[0].length;

        double[][] result = new double[numberOfFinalMatrixRows][numberOfFinalMatrixColumns];

        int numberOfCellsPerThread = (numberOfFinalMatrixRows * numberOfFinalMatrixColumns) / numberOfThreads;
        int firstIndex = 0;

        MultiplierThread[] multiplierThreads = new MultiplierThread[numberOfThreads];
        for (int threadIndex = numberOfThreads - 1; threadIndex >= 0; threadIndex--) {
            int lastIndex = firstIndex + numberOfCellsPerThread;

            if (threadIndex == 0) {
                lastIndex = numberOfFinalMatrixRows * numberOfFinalMatrixColumns;
            }

            multiplierThreads[threadIndex] = new MultiplierThread(a, b, result, firstIndex, lastIndex);
            multiplierThreads[threadIndex].start();

            firstIndex = lastIndex;
        }

        for (MultiplierThread multiplierThread : multiplierThreads)
            multiplierThread.join();

        return result;
    }


    class MultiplierThread extends Thread
    {
        private double[][] a;
        private double[][] b;
        private double[][] result;

        private final int firstIndex;
        private final int lastIndex;

        public MultiplierThread(final double[][] a,
                                final double[][] b,
                                final double[][] result,
                                final int firstIndex,
                                final int lastIndex)
        {
            this.a = a;
            this.b = b;
            this.result = result;
            this.firstIndex   = firstIndex;
            this.lastIndex    = lastIndex;
        }

        @Override
        public void run() {
            int size = b[0].length;

            for (int i = firstIndex; i < lastIndex; i++)
                calculateValue(i / size, i % size);
        }

        private void calculateValue(int row, int column)
        {
            int sum = 0;

            for (int i = 0; i < b.length; ++i)
                sum += a[row][i] * b[i][column];

            result[row][column] = sum;
        }
    }
}