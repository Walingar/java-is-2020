package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        int tempResult = 0;
        int currentOperand = 0;
        int sign = 1;

        if (expression == null) {
            throw new IllegalArgumentException("Input is null");
        }

        char[] inputStringToCharArray = expression.toCharArray();
        int expressionLength = inputStringToCharArray.length;


        for (int i = 0; i < expressionLength; i++) {

            char currentChar = inputStringToCharArray[i];

            if (Character.isWhitespace(currentChar)) {
                continue;
            }

            if (currentChar == '+') {
                tempResult = produceResult(tempResult, sign * currentOperand);
                sign = 1;
                currentOperand = 0;
            } else if (currentChar == '-') {
                tempResult = produceResult(tempResult, sign * currentOperand);
                sign = -1;
                currentOperand = 0;
            } else if (Character.isDigit(currentChar)) {
                try {
                    currentOperand = Math.addExact(Math.multiplyExact(currentOperand, 10), Character.getNumericValue(currentChar));
                } catch (ArithmeticException e) {
                    throw new ParseException("Input number is too long");
                }
            } else {
                throw new ParseException("Unknown symbol");
            }
        }
        return produceResult(tempResult, sign * currentOperand);
    }

    private int produceResult(int argumentA, int argumentB) throws ArithmeticException {
        try {
            return Math.addExact(argumentA, argumentB);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow");
        }
    }
}
