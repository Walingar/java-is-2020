package impl.matrix;

import api.matrix.ParallelMultiplier;
import api.matrix.ParallelMultiplierImpl;

public class ParallelMultiplierFactory {
    public static ParallelMultiplier getInstance(int maxThreadsCount) {
        return new ParallelMultiplierImpl(maxThreadsCount);
    }
}