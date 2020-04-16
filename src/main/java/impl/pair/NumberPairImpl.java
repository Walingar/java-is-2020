package impl.pair;

import api.pair.NumberPair;

import java.util.Objects;

public class NumberPairImpl<T extends Number, K extends Number> implements NumberPair<T, K> {
    private final T first;
    private final K second;

    public NumberPairImpl(T first, K second) {
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

        NumberPairImpl<?, ?> that = (NumberPairImpl<?, ?>) o;

        if (!Objects.equals(first, that.first)) return false;
        return Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
