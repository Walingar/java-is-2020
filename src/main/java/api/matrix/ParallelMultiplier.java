package api.matrix;

public interface ParallelMultiplier {
    double[][] mul(double[][] a, double[][] b) throws InterruptedException;
}
