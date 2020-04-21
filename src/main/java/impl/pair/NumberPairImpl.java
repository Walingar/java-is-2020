package impl.pair;

import api.pair.NumberPair;

import java.util.Objects;

public class NumberPairImpl<K extends Number, T extends Number> implements NumberPair<K, T> {

    private final K first;
    private final T second;

    public NumberPairImpl(K first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public K getFirst() {
        return null;
    }

    @Override
    public T getSecond() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberPairImpl)) return false;
        NumberPairImpl<?, ?> that = (NumberPairImpl<?, ?>) o;
        return Objects.equals(first, that.first) &&
                Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "NumberPairImpl{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
