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
        var rowsLength = a.length;
        var columnsLength = b[0].length;
        var result = new double[rowsLength][columnsLength];
        var threads = range(0, maxThreadsCount)
                .mapToObj(i -> new RunnerCalculator(a, b, result, i, maxThreadsCount))
                .map(Thread::new)
                .collect(toList());

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        return result;
    }
}

