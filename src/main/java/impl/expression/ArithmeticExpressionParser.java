package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ArithmeticExpressionParser implements ExpressionParser {
    private static final int BUFFER_CAPACITY = 11;

    private StringBuilder numberBuffer;

    private int expressionResult;

    public ArithmeticExpressionParser()
    {
        numberBuffer = new StringBuilder(BUFFER_CAPACITY);
    }

    @Override
    public int parse(String expression) throws ParseException, ArithmeticException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression shouldn't be null");
        }

        if (expression.isBlank()) {
            throw new IllegalArgumentException("Expression shouldn't be empty or whitespace");
        }

        expressionResult = 0;

        for (var i = 0; i < expression.length(); i++) {
            var expressionChar = expression.charAt(i);

            if (Character.isWhitespace(expressionChar)) {
                continue;
            }

            if ((expressionChar == '+' || expressionChar == '-') && numberBuffer.length() != 0) {
                parseNumberFromBuffer();
            }

            numberBuffer.append(expressionChar);
        }

        parseNumberFromBuffer();

        return expressionResult;
    }

    private void parseNumberFromBuffer() throws ParseException, ArithmeticException {
        int number;

        try {
            var stringNumber = numberBuffer.toString();
            number = Integer.parseInt(stringNumber);
        } catch (NumberFormatException e) {
            throw new ParseException("");
        }

        expressionResult = Math.addExact(expressionResult, number);
        numberBuffer.setLength(0);
    }
}