package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

import static java.lang.Integer.parseInt;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression may not be null");
        }
        ExpressionProcessor processor = new ExpressionProcessor();
        for (int index = 0; index < expression.length(); index++) {
            char character = expression.charAt(index);
            if (Character.isDigit(character)) {
                processor.processDigit(character);
            } else if (character == '+') {
                processor.processSign(Sign.PLUS);
            } else if (character == '-') {
                processor.processSign(Sign.MINUS);
            } else if (!Character.isWhitespace(character)) {
                String message = String.format("Illegal character '%c' encountered at index %d", character, index);
                throw new ParseException(message);
            }
        }
        return processor.getResult();
    }

    private enum Sign {
        PLUS,
        MINUS
    }

    private static class ExpressionProcessor {

        private final StringBuilder number = new StringBuilder();
        private int result = 0;
        private Sign sign;

        public void processDigit(char digit) {
            number.append(digit);
        }

        public void processSign(Sign newSign) throws ParseException {
            executeAddition();
            sign = newSign;
            number.setLength(0);
        }

        public int getResult() throws ParseException {
            executeAddition();
            return result;
        }

        private void executeAddition() throws ParseException {
            int currentNumber = getCurrentNumber();
            if (sign == Sign.MINUS) {
                result = Math.subtractExact(result, currentNumber);
            } else {
                result = Math.addExact(result, currentNumber);
            }
        }

        private int getCurrentNumber() throws ParseException {
            if (number.length() == 0) {
                return 0;
            }
            try {
                return parseInt(number.toString());
            } catch (Exception e) {
                throw new ParseException("Unexpected number: " + e.getMessage());
            }
        }
    }
}
