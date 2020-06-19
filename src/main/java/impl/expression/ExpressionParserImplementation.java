package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;


public class ExpressionParserImplementation implements ExpressionParser {
    public int parse(String expression) throws ParseException {
        int tempResult = 0;
        int curOperand = 0;
        boolean newOperand = true;
        boolean doubleOperator = false;
        int numberDigits = 0;

        if (expression == null) {
            throw new IllegalArgumentException();
        }

        char[] expressionCharArray = expression.toCharArray();
        for (int i = expressionCharArray.length - 1; i >= 0; i--) {
            if (Character.isWhitespace(expressionCharArray[i])) {
                newOperand = true;
                continue;
            }

            if (Character.isDigit(expressionCharArray[i])) {
                try {
                    if (newOperand && curOperand != 0) {
                        throw new ParseException("Two operands one after another");
                    }
                    curOperand = add(Math.multiplyExact(Character.getNumericValue(expressionCharArray[i]),
                            (int) Math.pow(10, numberDigits)), curOperand);
                    numberDigits++;
                    newOperand = false;
                    doubleOperator = false;
                } catch (ArithmeticException e) {
                    throw new ParseException("Number is too long");
                }
            } else if (expressionCharArray[i] == '+' || expressionCharArray[i] == '-') {
                if (doubleOperator) {
                    throw new ParseException("Two operators one after another");
                }
                curOperand = expressionCharArray[i] == '+' ? curOperand : -curOperand;
                tempResult = add(tempResult, curOperand);
                curOperand = 0;
                numberDigits = 0;
                doubleOperator = true;
            } else {
                throw new ParseException("Invalid symbol in the expression");
            }
        }
        return add(tempResult, curOperand);
    }

    private int add(int operand1, int operand2) {
        try {
            return Math.addExact(operand1, operand2);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow");
        }
    }
}
