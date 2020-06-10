package impl.matrix;

import api.matrix.ParallelMultiplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreadsCount;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        var rowsNumber = a.length;
        var columnsNumber = b[0].length;
        var result = new double[rowsNumber][columnsNumber];
        var threads = range(0, maxThreadsCount)
                .mapToObj(index -> new TasksMultiplier(a, b, result, index))
                .map(Thread::new)
                .collect(toList());
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }

    private class TasksMultiplier implements Runnable {

        private final double[][] a;
        private final double[][] b;
        private final double[][] result;

        private final int executorIndex;

        private final int columnsNumber;
        private final int tasksNumber;

        public TasksMultiplier(double[][] a, double[][] b, double[][] result, int executorIndex) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.executorIndex = executorIndex;
            this.columnsNumber = b[0].length;
            this.tasksNumber = a.length * columnsNumber;
        }

        @Override
        public void run() {
            var tasksCompleted = 0;
            for (var task = 0; task < tasksNumber; task++) {
                if (task % maxThreadsCount == executorIndex) {
                    var row = task / columnsNumber;
                    var column = task % columnsNumber;
                    result[row][column] = computeElement(row, column);
                    tasksCompleted++;
                }
            }
            System.out.format("Executor: %d, tasks completed: %d\n", executorIndex, tasksCompleted);
        }

        private double computeElement(int row, int column) {
            double result = 0;
            for (var i = 0; i < b.length; i++) {
                result += a[row][i] * b[i][column];
            }
            return result;
        }
    }
}
