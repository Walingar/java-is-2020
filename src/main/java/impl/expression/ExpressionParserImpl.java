package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException();
        }
        int previous = 0;
        int sign = 1;
        int number = 0;
        for (int i = 0; i < expression.length(); i++) {
            char element = expression.charAt(i);
            if (Character.isDigit(element)) {
                try {
                    number = Math.addExact(Math.multiplyExact(number, 10), Character.getNumericValue(element));
                } catch (ArithmeticException ex) {
                    throw new ParseException("parse long number");
                }
            } else if (element == '+' || element == '-') {
                previous = Math.addExact(previous, sign * number);
                number = 0;
                sign = (element == '+') ? 1 : -1;
            } else if (!Character.isWhitespace(element)) {
                throw new ParseException("parse nonNumber");
            }
        }
        return Math.addExact(previous, sign * number);
    }
}
