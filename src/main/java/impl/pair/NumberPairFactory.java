package impl.pair;

import api.pair.Pair;

public class NumberPairFactory {
    public static <T extends Number, K extends Number> Pair<T, K> of(T first, K second) {
        return new NumberPairImpl<>(first, second);
    }
}
