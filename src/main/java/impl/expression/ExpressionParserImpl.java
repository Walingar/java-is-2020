package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) throw new IllegalArgumentException();
        int previous = 0;
        int sign = 1;
        int number = 0;
        for (int i = 0; i < expression.length(); i++) {
            char element = expression.charAt(i);
            if (Character.isDigit(element)) {
                if ((long) number * 10 + Character.getNumericValue(element) > Integer.MAX_VALUE) {
                    throw new ParseException("parse long number");
                }
                number = number * 10 + Character.getNumericValue(element);
            } else {
                if ((element == '+') || (element == '-')) {
                    if (Integer.MAX_VALUE < ((long) previous + sign * number)) {
                        throw new ArithmeticException();
                    }
                    previous = previous + sign * number;
                    number = 0;
                    sign = (element == '+') ? 1 : -1;
                } else {
                    if (!Character.isWhitespace(element)) {
                        throw new ParseException("parse nonNumber");
                    }
                }
            }
        }
        if (Integer.MAX_VALUE < ((long) previous + sign * number)) {
            throw new ArithmeticException();
        }
        previous += sign * number;
        return previous;
    }
}
