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

        private final double[][] leftMatrix;
        private final double[][] rightMatrix;
        private final double[][] multiplicationResult;

        private final int executorIndex;

        private final int columnsNumber;
        private final int tasksCount;

        public TasksMultiplier(double[][] leftMatrix, double[][] rightMatrix, double[][] result, int executorIndex) {
            this.leftMatrix = leftMatrix;
            this.rightMatrix = rightMatrix;
            this.multiplicationResult = result;
            this.executorIndex = executorIndex;
            this.columnsNumber = rightMatrix[0].length;
            var overallTaskCount = leftMatrix.length * columnsNumber;
            this.tasksCount = executorIndex < overallTaskCount % maxThreadsCount ?
                    overallTaskCount / maxThreadsCount + 1 : overallTaskCount / maxThreadsCount;

        }

        @Override
        public void run() {
            for (var task = 0; task < tasksCount; task++) {
                var scaledTackNumber = task * maxThreadsCount + executorIndex;
                var row = scaledTackNumber / columnsNumber;
                var column = scaledTackNumber % columnsNumber;
                multiplicationResult[row][column] = computeElement(row, column);
            }
        }

        private double computeElement(int row, int column) {
            double computedElement = 0;
            for (var i = 0; i < rightMatrix.length; i++) {
                computedElement += leftMatrix[row][i] * rightMatrix[i][column];
            }
            return computedElement;
        }
    }

}
