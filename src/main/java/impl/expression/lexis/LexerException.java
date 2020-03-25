package impl.expression.lexis;

import api.expression.ParseException;

public class LexerException extends ParseException {
    public LexerException(String message, Exception inner) {
        super(message, inner);
    }

    public LexerException(String message) {
        super(message);
    }
}
