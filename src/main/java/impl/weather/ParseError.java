package impl.weather;

public class ParseError extends RuntimeException {
    public ParseError(String message) {
        super(message);
    }
}
