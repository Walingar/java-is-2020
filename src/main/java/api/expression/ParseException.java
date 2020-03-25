package api.expression;

public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Exception inner) {
        super(message, inner);
    }
}