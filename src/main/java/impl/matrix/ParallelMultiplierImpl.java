package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreads;

    public ParallelMultiplierImpl(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        Thread[] threads = new Thread[maxThreads];
        int aRows = a.length;
        int bRows = b.length;
        int bColumns = bRows == 0 ? 0 : b[0].length;
        int partSize = aRows / maxThreads;
        int residue = aRows % maxThreads;
        double[][] result = new double[aRows][bColumns];

       // int cElementsCount = aRowsCount * bColumnsCount;

        for (int currentThread = 0; currentThread < maxThreads; currentThread++) {
            int start = currentThread * partSize;
            int numberPart = currentThread + 1;
            int end = numberPart * partSize + (currentThread != maxThreads - 1 ? 0 : residue);
            threads[currentThread] = new Thread(new Multiplier(start, end, a, b, result));
            threads[currentThread].start();
        }

        try {
            for (int i = 0; i < maxThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }
}
