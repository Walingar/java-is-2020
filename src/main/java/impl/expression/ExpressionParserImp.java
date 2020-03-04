package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImp implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        var sum = 0;
        var commaExpression = deleteSpaces(expression);

        try {
            for (var num : commaExpression.split(",")) {
                if (sum >= Integer.MAX_VALUE) {
                    throw new ArithmeticException();
                }
                sum += Integer.parseInt(num);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
        return sum;
    }

    private static String deleteSpaces(String expression) {
        var expressionArray = expression.toCharArray();
        var resultExpression = new StringBuilder();
        int signsCount = 0;

        for (var value : expressionArray) {
            if (value == '+' || value == '-') {
                if (signsCount == 0) {
                    resultExpression.append(',');
                }
                resultExpression.append(value);
                signsCount++;
            } else if (value == ' ' || value == '\n' || value == '\t') {
                signsCount = 0;
            } else  {
                resultExpression.append(value);
            }
        }
        if (resultExpression.charAt(0) == ',') {
            resultExpression.delete(0, 1);
        }
        return resultExpression.toString();
    }
}