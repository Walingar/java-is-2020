package impl.pair;

import api.pair.Pair;

import java.util.Objects;

public class PairImpl<T, K> implements Pair<T, K> {

    private final T first;
    private final K second;


    PairImpl(T first, K second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {
        return first;
    }

    @Override
    public K getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairImpl<?, ?> pair = (PairImpl<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public String toString() {
        return "<" + first + ", " + second + ">";
    }

}
