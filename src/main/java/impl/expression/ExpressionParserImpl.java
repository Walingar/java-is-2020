package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        int result = 0;
        int currentOperand = 0;
        int sign = 1;

        if (expression == null) {
            throw new IllegalArgumentException("Input is null");
        }

        char[] inputStringToCharArray = expression.toCharArray();
        int expressionLength = inputStringToCharArray.length;


        for (int i = 0; i < expressionLength; i++) {

            if (Character.isWhitespace(inputStringToCharArray[i])) {
                continue;
            }

            if (inputStringToCharArray[i] == '+') {
                result = produceResult(result, sign * currentOperand);
                sign = 1;
                currentOperand = 0;
            } else if (inputStringToCharArray[i] == '-') {
                result = produceResult(result, sign * currentOperand);
                sign = -1;
                currentOperand = 0;
            } else if (Character.isDigit(inputStringToCharArray[i])) {
                try {
                    currentOperand = Math.addExact(Math.multiplyExact(currentOperand, 10), Character.getNumericValue(inputStringToCharArray[i]));
                } catch (ArithmeticException e) {
                    throw new ParseException("Input number is too long");
                }
            } else {
                throw new ParseException("Unknown symbol");
            }
        }
        result = produceResult(result, sign * currentOperand);

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
