package impl.pair;

import api.pair.Pair;
import factory.pair.NumberPairImpl;

public class NumberPairFactory {

    public static <K extends Number, T extends Number> Pair<K, T> of(K first, T second) {
        return new NumberPairImpl<>(first, second);
    }
}
