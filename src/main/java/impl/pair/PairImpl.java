package impl.pair;

import api.pair.Pair;

import java.util.Objects;

public class PairImpl<T, K> implements Pair<T, K> {
    private final T first;
    private final K second;

    public PairImpl(final T first, final K second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public final T getFirst() {
        return first;
    }

    @Override
    public final K getSecond() {
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PairImpl<?, ?> pair = (PairImpl<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
