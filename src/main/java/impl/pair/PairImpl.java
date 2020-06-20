package impl.pair;

import api.pair.Pair;

public class PairImpl<K, T> implements Pair<K, T> {

    private final K first;
    private final T second;

    public PairImpl(K first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public K getFirst() {
        return first;
    }

    @Override
    public T getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "PairImpl{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PairImpl)) {
            return false;
        }
        PairImpl<?, ?> pair = (PairImpl<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }
}
