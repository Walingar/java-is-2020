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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PairImpl)){
            return false;
        }
        PairImpl<?, ?> pair = (PairImpl<?, ?>) obj;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair [" + first.toString() + "," + second.toString() + "]";
    }
}
