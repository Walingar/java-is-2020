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
        char currentChar;

        for (int i = 0; i < expression.length(); i++) {

            currentChar = expression.charAt(i);

            if (Character.isWhitespace(currentChar)) {
                continue;
            }

            if (currentChar == '-') {
                result = updateVariable(result, operator, tempOperand);
                tempOperand = 0;
                operator = -1;
            } else if (currentChar == '+') {
                result = updateVariable(result, operator, tempOperand);
                tempOperand = 0;
                operator = 1;
            } else if (Character.isDigit(currentChar)) {
                try {
                    tempOperand = updateVariable(Character.getNumericValue(currentChar), 10, tempOperand);
                } catch (ArithmeticException e) {
                    throw new ParseException("Number too long");
                }
            } else {
                throw new ParseException("Invalid symbol");
            }
        }
        return updateVariable(result, operator, tempOperand);
    }

    private int updateVariable(int operandA, int operator, int operandB) {
        try {
            return Math.addExact(operandA, Math.multiplyExact(operator, operandB));
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow");
        }
    }
}
