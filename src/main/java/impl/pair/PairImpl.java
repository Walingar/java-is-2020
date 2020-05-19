package impl.pair;

import api.pair.Pair;


public class PairImpl<T, E> implements Pair<T, E> {
    private final T first;
    private final E second;

    PairImpl(T first, E second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {
        return this.first;
    }

    @Override
    public E getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return "Pair[ " + first + "," + second + "]";
    }
}
