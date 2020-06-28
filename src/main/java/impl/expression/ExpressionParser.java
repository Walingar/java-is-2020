package impl.expression;

import api.expression.ParseException;

public class ExpressionParser implements api.expression.ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {

        if (expression == null) {
            throw new IllegalArgumentException("expression is null");
        }

        int sum = 0;
        int numberSign = 1;

        for (String expressionPart : expression.split("[\\t\\n ]+")) {
            try {
                if (expressionPart.equals("+")) {
                    numberSign = 1;
                    continue;
                }
                if (expressionPart.equals("-")) {
                    numberSign = -1;
                    continue;
                }
                if (expressionPart.isEmpty()) {
                    continue;
                }

                int currentValue = Integer.parseInt(expressionPart);
                if (Math.abs(currentValue) >= Integer.MAX_VALUE - Math.abs(sum)) {
                    throw new ArithmeticException("overflow");
                }

                sum = sum + numberSign * currentValue;

            } catch (NumberFormatException e) {
                throw new ParseException("long type");
            }
        }

        return sum;
    }
};
