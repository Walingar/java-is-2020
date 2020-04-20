package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public final class ExpressionParserImpl implements ExpressionParser {
    private int accumulator;
    private int sign;
    private int operand;

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("expression must not be null");
        }
        reset();
        for (int position = 0; position < expression.length(); position++) {
            char symbol = expression.charAt(position);
            if (Character.isWhitespace(symbol)) {
                continue;
            }
            if (Character.isDigit(symbol)) {
                try {
                    operand = Math.addExact(Math.multiplyExact(operand, 10), Character.getNumericValue(symbol));
                } catch (ArithmeticException e) {
                    throw new ParseException("expression contains too large (or small) number at " + position);
                }
            } else if (symbol == '+' || symbol == '-') {
                computeLastOperation();
                if (symbol == '+') {
                    sign = 1;
                } else {
                    sign = -1;
                }
            } else {
                throw new ParseException("unexpected symbol \"" + symbol + "\" found at " + position);
            }
        }
        computeLastOperation();
        return accumulator;
    }

    private void reset() {
        accumulator = 0;
        sign = 1;
        operand = 0;
    }

    private void computeLastOperation() {
        accumulator = Math.addExact(accumulator, Math.multiplyExact(operand, sign));
        operand = 0;
    }
}
