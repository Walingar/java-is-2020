package impl.pair;

import api.pair.Pair;

public class NumberPairRealisation<K extends Number, T extends Number> implements Pair<K, T> {
    private K first;
    private T second;

    public NumberPairRealisation(K first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public K getFirst() {
        return null;
    }

    @Override
    public T getSecond() {
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

        NumberPairRealisation<K, T> other = (NumberPairRealisation<K, T>) obj;

        return first.equals(other.first)
                && second.equals(other.second);
    }

    @Override
    public String toString() {
        return first.toString().concat(" ").concat(second.toString());
    }
}

