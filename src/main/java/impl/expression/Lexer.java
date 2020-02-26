package impl.expression;

import api.expression.ParseException;
import impl.expression.lexis.*;
import impl.expression.lexis.Number;

public class Lexer {

    private String input;
    private int currentPosition;

    public Lexer(String input) {
//        reader = new StringReader(input);
//        Okay, StringReader seems not to support seek for some reason and is just cumbersome...
//        W'll just store the string itself, maybe it's better to use CharArray tho...
        this.input = input;
    }

    public Lexem getNextToken() throws ParseException {
        if (currentPosition >= input.length()) {
            return new Lexem(LexemType.EOE);
        }
        char symbol = input.charAt(currentPosition++);
        switch (symbol) {
            case '\t': //fallthrough
            case '\n': //fallthrough
            case ' ':  //Since we just don't recognize these, we are gonna skip to next here
                return getNextToken();
            case '+':
                return new Operator(OperatorType.Plus);
            case '-':
                return new Operator(OperatorType.Minus);
            default: {
                if (Character.isDigit(symbol)) {
                    var number = Character.getNumericValue(symbol);
//                    Don't really like next couple of lines, but other options were uglier
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
                            throw new LexerException(String.format("Number given is too big for int calculations! %d%d", number, nextDigit));
                        }
                    }
                    return new Number(number);
                } // Explicit else?
                throw new ParseException(String.format("Unsupported symbol: %s", symbol));
            }
        }
    }
}
