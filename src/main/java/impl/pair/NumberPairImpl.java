package impl.pair;

import api.pair.NumberPair;

public class NumberPairImpl<T extends Number, E extends Number> extends PairImpl<T, E> implements NumberPair<T, E> {
    NumberPairImpl(T first, E second) {
        super(first, second);
    }

}
