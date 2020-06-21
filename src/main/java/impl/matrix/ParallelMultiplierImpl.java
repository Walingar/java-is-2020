package impl.matrix;

import api.matrix.ParallelMultiplier;

import java.util.ArrayList;
import java.util.List;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int threadsNumber;
    private final List<Thread> threads;

    public ParallelMultiplierImpl(int threadsNumber) {
        this.threadsNumber = threadsNumber;
        this.threads = new ArrayList<>(threadsNumber);
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        var rows = a.length;
        var columns = b[0].length;
        var result = new double[rows][columns];
        for (int thread = 0; thread < threadsNumber; ++thread) {
            Thread t = new ThreadMultiplier(a, b, result, thread);
            threads.add(t);
        }
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        });
        return result;
    }

    private class ThreadMultiplier extends Thread {

        private final double[][] a;
        private final double[][] b;
        private final double[][] result;
        private final int currentThread;
        private final int tasksCount;

        private ThreadMultiplier(double[][] a, double[][] b, double[][] result, int currentThread) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.currentThread = currentThread;
            this.tasksCount = a.length * b[0].length;
        }

        @Override
        public void run() {
            int columns = b[0].length;
            for (int task = 0; task < tasksCount; ++task) {
                if (task % threadsNumber == currentThread) {
                    int row = task / columns;
                    int column = task % columns;
                    result[row][column] = getElement(row, column);
                }
            }
        }

        private double getElement(int row, int column) {
            int element = 0;
            for (int i = 0; i < a[0].length; ++i) {
                element += a[row][i] * b[i][column];
            }
            return element;
        }
    }
}