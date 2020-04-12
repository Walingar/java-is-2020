package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class Calculator implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression should not be null or empty");
        }
        int result = 0;
        var buffer = new StringBuilder();

        for (var curChar : expression.toCharArray()) {

            if (Character.isWhitespace(curChar)) {
                continue;
            }

            if (Character.isDigit(curChar)) {
                buffer.append(curChar);
                continue;
            }

            if (isOperator(curChar)) {
                if (buffer.length() != 0) {
                    result = getTempResult(buffer, result);
                    buffer.setLength(0);
                }
                buffer.append(curChar);
            }
        }
        result = getTempResult(buffer, result);

        return result;
    }

    private boolean isOperator(Character symbol) {
        return symbol == '+' || symbol == '-';
    }

    private int getTempResult(StringBuilder buffer, int result) throws ParseException {
        if (buffer.length() == 0) {
            return 0;
        }

        try {
            int number = Integer.parseInt(buffer.toString());
            result = Math.addExact(result,number);
            return result;
        } catch (NumberFormatException ex) {
            throw new ParseException("Cant parse number to int");
        }
    }
}
