package impl.pair;

public class PairFactory {
    public static <K, T> PairImpl of(K first, T second) {
        return new PairImpl(first, second);
    }
}
