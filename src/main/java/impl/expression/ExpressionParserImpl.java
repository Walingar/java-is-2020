package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Input is null");
        }
        int currentNumber = 0;
        int result = 0;
        int previousSign = 1;
        char[] chars = expression.toCharArray();
        for (char aChar : chars) {
            if (aChar == '+' || aChar == '-') {
                currentNumber *= previousSign;
                result = Math.addExact(result, currentNumber);
                currentNumber = 0;
                if (aChar == '+') {
                    previousSign = 1;
                } else {
                    previousSign = -1;
                }
            } else if (Character.isDigit(aChar)) {
                try {
                    currentNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), Character.getNumericValue(aChar));
                } catch (ArithmeticException ex) {
                    throw new ParseException("OverFlow");
                }
            } else if (!Character.isWhitespace(aChar)) {
                throw new ParseException("Invalid Symbols");
            }
        }
        return Math.addExact(result, previousSign * currentNumber);
    }
}
