package impl.expression;

import api.expression.ParseException;
import impl.expression.lexis.*;
import impl.expression.lexis.Number;

public class Lexer {

    private final String input;
    private int currentPosition;

    public Lexer(String input) {
        this.input = input;
    }

    public Lexeme getNextToken() throws ParseException {
        if (currentPosition >= input.length()) {
            return new Lexeme(LexemeType.EOE);
        }
        char symbol = input.charAt(currentPosition++);
        switch (symbol) {
            case '\t': //fallthrough
            case '\n': //fallthrough
            case '\r': //fallthrough
            case ' ':  //Since we just don't recognize these, we are gonna skip to next here
                return getNextToken();
            case '+':
                return new Operator(OperatorType.PLUS);
            case '-':
                return new Operator(OperatorType.MINUS);
            default: {
                if (Character.isDigit(symbol)) {
                    return new Number(consumeNumber(symbol));
                } // Explicit else?
                throw new ParseException(String.format("Unsupported symbol: %s", symbol));
            }
        }
    }

    private int consumeNumber(char symbol) throws LexerException {
        var number = Character.getNumericValue(symbol);
        while (currentPosition < input.length()) {
            char nextChar = input.charAt(currentPosition);
            if (!Character.isDigit(nextChar)) {
                break;
            }
            int nextDigit = Character.getNumericValue(nextChar);
            try {
                number = Math.addExact(Math.multiplyExact(10, number), nextDigit);
                currentPosition++;
            } catch (ArithmeticException e) { //gotta rethrow proper exception here FOR THE TESTS
                var message = String.format(
                        "Number given is too big for int calculations! %d%d", number, nextDigit);
                throw new LexerException(message, e);
            }
        }
        return number;
    }
}
