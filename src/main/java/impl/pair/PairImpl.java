package impl.pair;

import api.pair.Pair;

import java.util.Objects;

public class PairImpl<F, S> implements Pair<F, S> {
    private F first;
    private S second;

    public PairImpl(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public String toString() {
        return "PairImpl(" + first + ", " + second + ')';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PairImpl)) {
            return false;
        }

        PairImpl<?, ?> pair = (PairImpl<?, ?>) o;

        return Objects.equals(getFirst(), pair.getFirst()) &&
               Objects.equals(getSecond(), pair.getSecond());
    }
}