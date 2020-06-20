package impl.pair;

import api.pair.NumberPair;

import java.util.Objects;

public class NumberPairImpl<T extends Number, K extends Number>  extends PairImpl<T, K> implements NumberPair<T, K> {

    public NumberPairImpl(T first, K second) {
        super(first, second);
    }
}