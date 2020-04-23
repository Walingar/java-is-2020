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
    public String toString() {
        return first.toString() + ':' + second.toString();
    }

    @Override
    public boolean equals(Object o) {
        return this.getClass() == o.getClass() && this.toString().equals(o.toString());
    }
}
