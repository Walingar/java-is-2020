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
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];
        List<Thread> threadList = new ArrayList<>();

        for (int threadIndex = 0; threadIndex < maxThreadsCount; threadIndex++) {
            threadList.add(new Thread(new TasksMultiplier(firstMatrix, secondMatrix, result, threadIndex)));
        }

        threadList.forEach(Thread::start);
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }

    private class TasksMultiplier implements Runnable {

        private final double[][] firstMatrix;
        private final double[][] secondMatrix;
        private final double[][] result;

        private final int columnsCount;
        private final int tasksCount;

        private final int executorIndex;


        public TasksMultiplier(double[][] firstMatrix, double[][] secondMatrix, double[][] result, int executorIndex) {
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;
            this.result = result;
            this.executorIndex = executorIndex;
            this.columnsCount = secondMatrix[0].length;
            this.tasksCount = calculateThreadTasksCount();
        }

        @Override
        public void run() {
            for (int task = 0; task < tasksCount; task++) {
                int scaledTaskNumber = task * maxThreadsCount + executorIndex;
                int row = scaledTaskNumber / columnsCount;
                int column = scaledTaskNumber % columnsCount;
                result[row][column] = calculateTaskResult(row, column);
            }
        }

        private double calculateTaskResult(int row, int column) {
            double result = 0;
            for (var i = 0; i < secondMatrix.length; i++) {
                result += firstMatrix[row][i] * secondMatrix[i][column];
            }
            return result;
        }

        private int calculateThreadTasksCount() {
            int overallTaskCount = firstMatrix.length * columnsCount;
            int threadTasksCount = overallTaskCount / maxThreadsCount;
            if (executorIndex < overallTaskCount % maxThreadsCount) {
                threadTasksCount++;
            }
            return threadTasksCount;
        }

    }
}
