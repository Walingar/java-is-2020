package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

import java.util.concurrent.atomic.AtomicInteger;

public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        int sum = 0;
        var number = new StringBuilder();

        for (var character : expression.toCharArray()) {
            if (character == '+' || character == '-') {
                if (number.toString().equals("")) {
                    number.append(character);
                    continue;
                }
                try {
                    sum = Math.addExact(sum, Integer.parseInt(number.toString()));
                } catch (NumberFormatException e) {
                    throw new ParseException(e.getMessage());
                }
                number.setLength(0);
                number.append(character);
            } else if (java.lang.Character.isWhitespace(character)) {
                continue;
            } else  {
                number.append(character);
            }
        }
        try {
            sum = Math.addExact(sum, Integer.parseInt(number.toString()));
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }
        return sum;
    }
}