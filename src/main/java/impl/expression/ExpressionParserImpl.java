package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("String was null");
        }

        char[] symbols = expression.toCharArray();
        var result = 0;
        var sign = 1;
        var number = 0;

        for (var symbol : symbols) {
            switch (symbol) {
                case '+':
                    result = Math.addExact(result, sign * number);
                    number = 0;
                    sign = 1;
                    break;
                case '-':
                    result = Math.addExact(result, sign * number);
                    number = 0;
                    sign = -1;
                    break;
                case ' ':
                case '\t':
                case '\n':
                    result = Math.addExact(result, sign * number);
                    number = 0;
                    break;
                default:
                    if (Character.isDigit(symbol)) {
                        try {
                            number = Math.addExact(Math.multiplyExact(number, 10), Character.getNumericValue(symbol));
                        } catch (ArithmeticException ex) {
                            throw new ParseException("Number is too long");
                        }
                    } else {
                        throw new ParseException("Not a valid symbol");
                    }
                    break;
            }
        }

        return Math.addExact(result, sign * number);
    }
}
