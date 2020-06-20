package impl.pair;

import api.pair.Pair;

public class PairFactory {
    public static <T, K> Pair<T, K> of(T first, K second) {
        return new PairImpl<>(first ,second);
    }
}