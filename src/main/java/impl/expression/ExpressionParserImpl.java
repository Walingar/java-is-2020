package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException, ArithmeticException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        if (expression.isBlank()) {
            throw new IllegalArgumentException("Expression is empty");
        }
        int result = 0;
        StringBuilder currentNumber = new StringBuilder();
        // parsing string
        for (char currentChar : expression.toCharArray()) {
            // if whitespace
            if (Character.isWhitespace(currentChar)) {
                continue;
            }
            // adding digit
            if (Character.isDigit(currentChar)) {
                currentNumber.append(currentChar);
                continue;
            }
            // sign processing
            if (currentChar == '+' || currentChar == '-') {
                if (currentNumber.length() != 0) {
                    result = Math.addExact(result, getNumber(currentNumber));
                    currentNumber = new StringBuilder();
                }
                currentNumber.append(currentChar);
                continue;
            }
            throw new ParseException(String.format("Illegal character '%c' ", currentChar));
        }
        if (currentNumber.length() != 0) {
            result = Math.addExact(result, getNumber(currentNumber));
        }
        return result;
    }

    private int getNumber(StringBuilder currentNumber) throws ParseException {
        try {
            return Integer.parseInt(currentNumber.toString());
        } catch (NumberFormatException e) {
            throw new ParseException("Not valid integer format");
        }
    }
}