package impl.expression;

import api.expression.ParseException;

public class ExpressionParser implements api.expression.ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {

        if (expression == null) {
            throw new IllegalArgumentException("expression is null");
        }

        String[] expressionParts = expression.split("[\\t\\n ]+");

        int sum = 0;
        int numberSign = 1;

        for (int i = 0; i < expressionParts.length; i++) {
            try {
                if (expressionParts[i].equals("+")) {
                    numberSign = 1;
                    continue;
                }
                if (expressionParts[i].equals("-")) {
                    numberSign = -1;
                    continue;
                }
                if (expressionParts[i].isEmpty()) {
                    continue;
                }

                int temp = Integer.parseInt(expressionParts[i]);
                if (Math.abs(temp) >= Integer.MAX_VALUE - Math.abs(sum)) {
                    throw new ArithmeticException("overflow");
                }

                sum = sum + numberSign * temp;

            } catch (NumberFormatException e) {
                throw new ParseException("long type");
            }
        }

        return sum;
    }
};
