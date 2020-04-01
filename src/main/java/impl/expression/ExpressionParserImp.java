package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImp implements ExpressionParser {


    public int parse(String s) throws ParseException {
        if (s == null) {
            throw new IllegalArgumentException("null input");
        }
        int currentNumber = 0;
        int result = 0;
        int previousSign = 1;
        for (char currentSymbol : s.toCharArray()) {
            if (currentSymbol == '-' || currentSymbol == '+') {
                currentNumber *= previousSign;
                result = Math.addExact(result, currentNumber);
                currentNumber = 0;
                previousSign = ((currentSymbol == '-') ? -1 : 1);
            } else if (Character.isDigit(currentSymbol)) {
                try {
                    currentNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), Character.getNumericValue(currentSymbol));
                } catch (ArithmeticException ex) {
                    throw new ParseException("OverFlow");
                }
            } else if (!Character.isWhitespace(currentSymbol)) {
                throw new ParseException("Invalid Symbols");
            }
        }
        return Math.addExact(result, previousSign * currentNumber);
    }


}