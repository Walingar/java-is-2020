package impl.matrix;

public class Runner implements Runnable {
    private final double[][] a;
    private final double[][] b;
    private final double[][] result;
    private final int workerIndex;
    private final int tasksCount;
    private final int maxThreadsCount;

    Runner(double[][] a, double[][] b, double[][] result, int workerIndex, int maxThreadsCount) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.workerIndex = workerIndex;
        this.maxThreadsCount = maxThreadsCount;
        var totalTaskCount = a.length * b[0].length;
        this.tasksCount = workerIndex < totalTaskCount % maxThreadsCount ?
                totalTaskCount / maxThreadsCount + 1 : totalTaskCount / maxThreadsCount;
    }

    @Override
    public void run() {
        for (var i = 0; i < tasksCount; i++) {
            var taskNum = i * maxThreadsCount + workerIndex;
            var row = taskNum / b[0].length;
            var column = taskNum % b[0].length;
            result[row][column] = calculate(row, column);
        }
    }

    private double calculate(int row, int column) {
        double result = 0;
        for (var i = 0; i < b.length; i++) {
            result += a[row][i] * b[i][column];
        }

        return result;
    }
}
