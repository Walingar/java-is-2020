package impl.expression.helper;


@FunctionalInterface
public interface ThrowingFunction<K, V, E extends Throwable> {
    V generate(K k) throws E;
}
