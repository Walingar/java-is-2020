package impl.matrix;

import api.matrix.ParallelMultiplier;

public class ParallelMultiplierFactory {
    public static ParallelMultiplier getInstance(int maxThreadsCount) {
        return new ParallelMultiplierRealisation(maxThreadsCount);
    }
}
