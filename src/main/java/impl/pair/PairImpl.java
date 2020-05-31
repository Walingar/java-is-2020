package impl.pair;

import api.pair.Pair;

public class PairImpl<K, T> implements Pair<K, T> {
    private final K first;
    private final T second;

    PairImpl(K first, T second) {
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
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PairImpl<?, ?> pair = (PairImpl<?, ?>) obj;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public String toString() {
        return "<" + first.toString() + "," + second.toString() + ">";
    }
}
