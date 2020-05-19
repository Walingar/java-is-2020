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
        return "PairImpl <" + first.toString() + "," + second.toString() + ">";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PairImpl)) {
            return false;
        }
        PairImpl<?, ?> inputPair = (PairImpl<?, ?>) object;
        return Objects.equals(first, inputPair.first) &&
                Objects.equals(second, inputPair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

}
