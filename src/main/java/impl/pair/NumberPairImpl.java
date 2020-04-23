package impl.pair;

import api.pair.NumberPair;

public class NumberPairImpl<K extends Number, T extends Number> extends PairImpl<K, T> implements NumberPair<K, T> {
    NumberPairImpl(K first, T second) {
        super(first, second);
    }
}
