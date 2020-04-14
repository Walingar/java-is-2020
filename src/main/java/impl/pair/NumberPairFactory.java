package impl.pair;

public class NumberPairFactory {
    public static <K extends Number, T extends Number> NumberPairImpl<K, T> of(K first, T second) {
        return new NumberPairImpl<K, T>(first, second);
    }
}
