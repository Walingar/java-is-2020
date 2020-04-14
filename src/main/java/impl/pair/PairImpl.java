package impl.pair;

import api.pair.Pair;


public class PairImpl<T, E> implements Pair {
    private final T first;
    private final E second;

    PairImpl(T first, E second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Object getFirst() {
        return this.first;
    }

    @Override
    public Object getSecond() {
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
