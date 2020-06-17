package impl.pair;

import api.pair.Pair;

import java.util.Objects;

public class PairImpl<T, E> implements Pair<T, E> {
    private final T first;
    private final E second;

    PairImpl(T first, E second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {
        return first;
    }

    @Override
    public E getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PairImpl<?, ?> pair = (PairImpl<?, ?>) obj;
        return Objects.equals(getFirst(), pair.getFirst()) && Objects.equals(getSecond(), pair.getSecond());
    }

    @Override
    public String toString() {
        return "Pair[ " + first + "," + second + "]";
    }
}
