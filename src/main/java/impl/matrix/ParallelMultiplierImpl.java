package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;

public class ParallelMultiplierImpl implements ParallelMultiplier {


    private int maxThreadsCount;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
        //Since we know this we can actually try to have private array of worker threads and reuse those, but meh
    }

    /**
     * Basically here's an algorithm for c[n][t] = a[n][m] * b[m][t] :
     *   (1)  for (int l = 0; l < n * t; l++) {
     *             int i = l % n;
     *             int j = l / n;
     *
     *   (2)       for (int k = 0; k < m; k++) {
     *                 c[i][j] = c[i][j] + a[i][k] * b[k][j];
     *             }
     *         }
     *  WE can parallel (1) and (2),
     *  if we parallel (2) we'll need to modify same c[i][j] in each thread and this has two solutions:
     *     a) aggregate temp value in each thread and then wait for it to finish and just sum all the values
     *     b) use atomics (long <-> bits <-> double)
     *  OR WE CAN EASILY PARALLEL (1) and just not care since we get no overlaps =)
     */
    @Override
    public double[][] mul(double[][] a, double[][] b) {
        int aWidth = a.length;
        if (aWidth == 0 || b.length == 0) {
            return new double[0][];
        }
        if (b.length != a[0].length) {
            throw new IllegalArgumentException("Matricies should have aligned sizes");
        }
        int bHeight = b[0].length;

        var totalIterations = aWidth * bHeight;

        var reasonableParallelismDegree = Math.min(totalIterations, this.maxThreadsCount);

        var iterationsPerThread = totalIterations / reasonableParallelismDegree;
        // Here we probably have some iterations left, so we can either throw all of them into first of last thread
        // OR we can try to split those equally...
        var iterationsLeft = totalIterations - iterationsPerThread * reasonableParallelismDegree;

        double[][] c = new double[aWidth][bHeight];

        var threads = new ArrayList<Thread>();
        int startIndex = 0;
        for (int threadNum = 0; threadNum < reasonableParallelismDegree; threadNum++) {
//            var iterations = threadNum < iterationsLeft ? iterationsPerThread : iterationsPerThread + 1;
//            Ugly either way =(
            var end = startIndex + iterationsPerThread;
            if (threadNum < iterationsLeft) { //I'd try to split as equally as possible
                end += 1;
            }
            // A neat (but ugly from code design POV) trick of passing references to alter contents =)
            var thread = new Thread(new Worker(a, b, c, startIndex, end));
            // we can start the thread right away, or wait until all are constructed...
            threads.add(thread);
            thread.start();
            startIndex = end;
        }
        for (var thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(String.format("something went wrong during parallel execution %s", e.getMessage()));
            }
        }
        return c;
    }

    private static class Worker implements Runnable{

        private double[][] a;
        private double[][] b;
        private double[][] c;
        private int start;
        private int end;

        public Worker(double[][] a, double[][] b, double[][] c, int start, int end){
            this.a = a;
            this.b = b;
            this.c = c;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            var width = a.length;
            var height = b.length;

//            Yeah, I don't know, math code is usually like that to align with the notation,
//            So I'm not sure about the namings here...
            for (int l = start; l < end; l++) {
                int i = l % width;
                int j = l / width;

                for (int k = 0; k < height; k++) {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                }
            }
        }
    }
}
