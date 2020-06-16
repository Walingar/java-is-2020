package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {
    int maxThreadsCount;

    ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        Thread[] t = new Thread[maxThreadsCount];
        double[][] c = new double[a.length][b[0].length];

        int step = (int) Math.ceil((double) a.length / maxThreadsCount);
        int startI = 0;
        int finishI = startI + step;
        for (int i = 0; i < maxThreadsCount; i++) {
            t[i] = new Thread(new RunnableImpl(a, b, c, startI, finishI));
            t[i].start();

            if (finishI == a.length) {
                maxThreadsCount = i + 1;
                break;
            }
            startI += step;
            finishI = Math.min(finishI + startI, a.length);
        }

        try {
            for (int i = 0; i < maxThreadsCount; i++) {
                t[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error!");
        }

        return c;
    }
}
