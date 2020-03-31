package impl.expression.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Matcher<S, T, E extends Throwable> {

    private Map<Function<S, Boolean>, ThrowingFunction<S, T, E>> cases;
    private Class<E> exceptionType;
    private String errorMessage;

    public Matcher() {
        cases = new HashMap<>();
    }

    public void match(Function<S, Boolean> condition, ThrowingFunction<S, T, E> generator) {
        cases.put(condition, generator);
    }

    public T produce(S param) throws E {
        for (var entry : cases.entrySet()) {
            var condition = entry.getKey();
            if (condition.apply(param)) {
                var generator = entry.getValue();
                return generator.generate(param);
            }
        }
        if (exceptionType != null) { //We've fallen through all the cases we've had, so default throw goes here...
            try {
                throw exceptionType.getDeclaredConstructor(String.class).newInstance(errorMessage);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalStateException("Invalid matching condition! Also Exception with no String constructor");
            }
        }
        throw new IllegalStateException("Invalid matching condition!");
    }

    public void orThrow(Class<E> exception, String errorMessage) {
        this.exceptionType = exception;
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
