package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {

        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }

        int result = 0;
        int tempOperand = 0;
        int operator = 1;

        for (char currentChar : expression.toCharArray()) {

            if (Character.isWhitespace(currentChar)) {
                continue;
            }

            if (currentChar == '-' || currentChar == '+') {
                result = updateResult(result, operator, tempOperand);
                tempOperand = 0;
                if (currentChar == '-') {
                    operator = -1;
                } else {
                    operator = 1;
                }
            } else if (Character.isDigit(currentChar)) {
                try {
                    tempOperand = Math.addExact(Math.multiplyExact(tempOperand, 10), Character.getNumericValue(currentChar));
                } catch (ArithmeticException e) {
                    throw new ParseException("Too long number");
                }
            } else {
                throw new ParseException("Invalid symbol");
            }
        }
        result = updateResult(result, operator, tempOperand);

        return result;
    }

    private int updateResult(int operandA, int operator, int operandB) throws ArithmeticException {
        try {
            return Math.addExact(operandA, Math.multiplyExact(operator, operandB));
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow");
        }
    }
}
