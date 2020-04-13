package impl.pair;

import api.pair.NumberPair;

class NumberPairImpl<T extends Number, K extends Number> extends PairImpl<T, K> implements NumberPair<T, K> {

    NumberPairImpl(T first, K second) {
        super(first, second);
    }

}
