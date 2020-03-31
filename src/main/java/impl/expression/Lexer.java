package impl.expression;

import impl.expression.helper.Matcher;
import impl.expression.lexis.*;
import impl.expression.lexis.Number;

public class Lexer {

    private final String input;
    private int currentPosition;
    private Matcher<Character, Lexeme, LexerException> matcher;

    public Lexer(String input) {
        this.input = input;
//        Basically all lexical analysis happens here is sort-of pattern-matching analog for Java
        matcher = new Matcher<>();
        matcher.match(Character::isWhitespace, s -> getNextToken());
        matcher.match(c -> c.equals('+'), s -> new Operator(OperatorType.PLUS));
        matcher.match(c -> c.equals('-'), s -> new Operator(OperatorType.MINUS));
        matcher.match(Character::isDigit, s -> new Number(consumeNumber(s)));
        matcher.orThrow(LexerException.class, "Improperly formatted input");
    }

    public Lexeme getNextToken() throws LexerException {
        if (currentPosition >= input.length()) {
            return new Lexeme(LexemeType.EOE);
        }
        char symbol = input.charAt(currentPosition++);
//        Can be done prettier
        matcher.setErrorMessage(String.format("Improperly formatter input! %c at position %d", symbol, currentPosition));
        return matcher.produce(symbol);
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
