package impl.pair;

import api.pair.NumberPair;

public class NumberPairFactory {
    public static <K extends Number, T extends Number> NumberPair<K, T> of(K first, T second) {
        return new NumberPairImpl<>(first, second);
    }
}
