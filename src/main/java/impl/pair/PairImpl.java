package impl.pair;

import api.pair.Pair;

import java.util.Objects;

public class PairImpl<K, T> implements Pair<K, T> {
    K first;
    T second;

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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Pair) {
            PairImpl<?, ?> temp = (PairImpl<?, ?>) obj;
            return Objects.equals(first, temp.first) && Objects.equals(second, temp.second);
        }
        return false;
    }

    public String toString() {
        return "<" + first.toString() + ", " + second.toString() + ">";
    }
}
