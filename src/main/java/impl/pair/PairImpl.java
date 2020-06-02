package impl.pair;

import api.pair.Pair;

import java.util.Objects;

public class PairImpl<T, K> implements Pair<T, K> {
    private final T _1;
    private final K _2;

    public PairImpl(T _1, K _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public T getFirst() {
        return _1;
    }

    @Override
    public K getSecond() {
        return _2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairImpl<?, ?> pair = (PairImpl<?, ?>) o;
        return Objects.equals(_1, pair._1) &&
                Objects.equals(_2, pair._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }
}
