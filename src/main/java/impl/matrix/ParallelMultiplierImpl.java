package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreads;

    public ParallelMultiplierImpl(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        int aRows = a.length;
        int bRows = b.length;
        int bColumns = bRows == 0 ? 0 : b[0].length;
        int nBar = aRows / maxThreads;
        int residue = aRows % maxThreads;
        double[][] result = new double[aRows][bColumns];
        for (int threadCurrent = 0; threadCurrent < maxThreads; threadCurrent++) {
            int start = threadCurrent * nBar;
            int end = (threadCurrent != maxThreads - 1) ? (threadCurrent + 1) * nBar : (threadCurrent + 1) * nBar + residue;
            Thread thread = new Thread(new Multiplier(start, end, bColumns, bRows, a, b, result));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
}
