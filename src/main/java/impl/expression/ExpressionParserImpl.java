package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        int result = 0;
        int container = 0;
        int sign = 1;

        if (expression == null) {
            throw new IllegalArgumentException("Input is null");
        }

        char[] temp = expression.toCharArray();
        int expressionLength = temp.length;


        for (int i = 0; i < expressionLength; i++) {

            if (Character.isWhitespace(temp[i])) {
                continue;
            }

            if (temp[i] == '+') {
                result = produceResult(result, sign * container);
                sign = 1;
                container = 0;
            } else if (temp[i] == '-') {
                result = produceResult(result, sign * container);
                sign = -1;
                container = 0;
            } else if (Character.isDigit(temp[i])) {
                try {
                    container = Math.addExact(Math.multiplyExact(container, 10), Character.getNumericValue(temp[i]));
                } catch (ArithmeticException e) {
                    throw new ParseException("Input number is too long");
                }
            } else {
                throw new ParseException("Unknown symbol");
            }
        }
        result = produceResult(result, sign * container);

        return result;
    }

    private int produceResult(int argumentA, int argumentB) throws ArithmeticException {
        try {
            return Math.addExact(argumentA, argumentB);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow");
        }
    }
}
