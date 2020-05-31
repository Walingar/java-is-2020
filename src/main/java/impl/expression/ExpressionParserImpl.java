package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {

    private enum Operator {
        ADDITION,
        SUBSTRACTION
    }

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("`expression` must be not null");
        }

        int result = 0;
        int currentNumber = 0;
        Operator currentOperator = Operator.ADDITION;

        for (var currentSymbol : expression.toCharArray()) {
            if (currentSymbol == '+' || currentSymbol == '-') {
                if (currentOperator == Operator.ADDITION) {
                    result = Math.addExact(result, currentNumber);
                } else {
                    result = Math.addExact(result, -currentNumber);
                }

                if (currentSymbol == '+') {
                    currentOperator = Operator.ADDITION;
                } else {
                    currentOperator = Operator.SUBSTRACTION;
                }
                currentNumber = 0;
            } else if (Character.isDigit(currentSymbol)) {
                try {
                    currentNumber = Math.addExact(Math.multiplyExact(currentNumber, 10),
                            Character.getNumericValue(currentSymbol));
                } catch (ArithmeticException e) {
                    throw new ParseException("Number is too big for int-type.");
                }
            } else {
                if (!Character.isWhitespace(currentSymbol)) {
                    throw new ParseException(String.format("Symbol `%s` is not valid. " +
                            "It must be digit or `+` or `-` or whitespace", currentSymbol));
                }
            }

        }

        if (currentOperator == Operator.ADDITION) {
            result = Math.addExact(result, currentNumber);
        } else {
            result = Math.addExact(result, -currentNumber);
        }

        return result;
    }


}
