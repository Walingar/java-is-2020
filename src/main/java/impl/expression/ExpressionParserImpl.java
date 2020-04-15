package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    private final StringBuilder currentNumber;

    public ExpressionParserImpl() {
        currentNumber = new StringBuilder();
    }

    public int parse(String expression) throws ParseException {

        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        if (expression.isBlank()) {
            throw new IllegalArgumentException("Expression is empty");
        }
        try {
            return Integer.parseInt(expression);
        } catch (ArithmeticException e) {
            int result = 0;
            for (int pos = 0; pos < expression.length(); pos++) {

                char processed_char = expression.charAt(pos);
                if (Character.isWhitespace(processed_char)) {
                    continue;
                }
                if (Character.isDigit(processed_char)) {
                    currentNumber.append(processed_char);
                } else {
                    if (processed_char == '+' || processed_char == '-') {
                        if (currentNumber.length() != 0) {
                            result = calculate(result, getValue(currentNumber));
                        }
                        currentNumber.append(processed_char);
                    } else {
                        throw new ParseException("Unknown character encountered during parsing in position: " + pos);
                    }
                }
            }
            if (currentNumber.length() != 0) {
                result = calculate(result, getValue(currentNumber));
            }
            return result;
        }
    }

    private int getValue(StringBuilder number) throws ParseException {
        try {
            var stringNumber = number.toString();
            return Integer.parseInt(stringNumber);
        } catch (NumberFormatException e) {
            throw new ParseException("Non-valid integer format");
        }
    }

    private int calculate(int a, int b) {
        currentNumber.setLength(0);
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Unable to compute math operation");
        }
    }
}
