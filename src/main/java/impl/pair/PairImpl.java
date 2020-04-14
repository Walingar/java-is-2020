package impl.pair;

import api.pair.Pair;

public class PairImpl<K, T> implements Pair<K, T> {
    private final K first;
    private final T second;

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
        return getClass() == obj.getClass() && toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return "<" + first.toString() + "," + second.toString() + ">";
    }

    PairImpl(K first, T second) {
        this.first = first;
        this.second = second;
    }
}
