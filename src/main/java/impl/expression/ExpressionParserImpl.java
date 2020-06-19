package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    private final StringBuilder currentNumber;
    private final int maxIntLen = (int) Math.floor(Math.log10(Integer.MAX_VALUE)) + 2; // +1 for - in negatives, +1 to compensate floor
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
        int expressionLength = expression.length();
        try {
            checkNumber(expressionLength);
            return Integer.parseInt(expression);
        } catch (NumberFormatException | ParseException  e) {
            currentNumber.setLength(0);
            int result = 0;
            for (int pos = 0; pos < expressionLength; pos++) {

                char processed_char = expression.charAt(pos);
                if (Character.isWhitespace(processed_char)) {
                    continue;
                }
                if (Character.isDigit(processed_char)) {
                    currentNumber.append(processed_char);
                    checkNumber(currentNumber.length());
                } else {
                    if (processed_char == '+' || processed_char == '-') {
                        if (currentNumber.length() != 0) {
                            result = calculate(result, getValue(currentNumber));
                            currentNumber.setLength(0);
                        }
                        currentNumber.append(processed_char);
                    } else {
                        throw new ParseException("Unknown character encountered during parsing in position: " + pos);
                    }
                }
            }
            if (currentNumber.length() != 0) {
                return calculate(result, getValue(currentNumber));
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
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Unable to compute math operation");
        }
    }

    private void checkNumber(int len) throws ParseException {
        if (len > maxIntLen) {
            throw new ParseException("Value is too large for int");
        }
    }
}
