package impl.pair;

import api.pair.Pair;

import java.util.Objects;

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
    public String toString() {
        return first.toString() + ':' + second.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PairImpl)) {
            return false;
        }
        PairImpl<?,?> pair = (PairImpl<?,?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }
}
