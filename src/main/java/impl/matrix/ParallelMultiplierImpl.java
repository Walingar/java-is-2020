package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierImpl implements ParallelMultiplier {

    private final int maxThreadsCount;

    public ParallelMultiplierImpl(int maxThreadsCount) {
        this.maxThreadsCount = maxThreadsCount;
    }

    @Override
    public double[][] mul(double[][] a, double[][] b) {
        var aNumRows = a.length;
        var aNumColumns = a[0].length;
        var bNumRows = b.length;
        var bNumColumns = b[0].length;


        double[][] result = new double[aNumRows][bNumColumns];

        for (var i = 0; i < aNumRows; i++) {
            for (var j = 0; j < bNumColumns; j++) {
                double dot = 0.0;
                for (var k = 0; k < aNumColumns; k++) {
                    dot += a[i][k] * b[k][j];
                }
                result[i][j] = dot;
            }
        }
        return result;
    }
}
