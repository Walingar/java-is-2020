package impl.pair;

import api.pair.Pair;

public class PairRealisation<T, K> implements Pair<T, K> {
    private T first;
    private K second;

    public PairRealisation(T first, K second){
        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {
        return null;
    }

    @Override
    public K getSecond() {
        return null;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        PairRealisation<T, K> other = (PairRealisation<T, K>) obj;

        return first.equals(other.first)
                && second.equals(other.second);
    }

    @Override
    public String toString() {
        return first.toString().concat(" ").concat(second.toString());
    }
}

