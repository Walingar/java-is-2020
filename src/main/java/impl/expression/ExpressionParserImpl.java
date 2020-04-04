package impl.expression;


import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        IntegerParser intParser = new IntegerParser(expression);

        int expressionResult = 0;
        while (intParser.hasNextInt()) {
            expressionResult = Math.addExact(expressionResult, intParser.nextInt());
        }

        return expressionResult;
    }


    public static class IntegerParser {

        private final String expression;
        private int currentPosition;
        private int previousPosition;
        private char operator;

        IntegerParser(String expression) throws IllegalArgumentException {
            if (expression == null) {
                throw new IllegalArgumentException("expected string, got null");
            }
            this.expression = expression;
            currentPosition = 0;
            previousPosition = 0;
            operator = '+';
        }

        boolean isEof() {
            return currentPosition == expression.length();
        }

        boolean hasNextInt() {
            return !isEof();
        }

        int nextInt() throws ParseException {

            while (!isEof()) {
                char currentCharacter = expression.charAt(currentPosition);

                if (!isExpectedSymbol(currentCharacter)) {
                    throw new ParseException("unexpected symbol");
                }

                if (Character.isWhitespace(currentCharacter) && previousPosition == currentPosition) {
                    previousPosition++;
                }

                if (isOperator(currentCharacter)) {
                    int result = convertToInt(previousPosition, currentPosition, operator);
                    currentPosition++;
                    previousPosition = currentPosition;
                    operator = currentCharacter;
                    return result;
                }

                currentPosition++;
            }

            return convertToInt(previousPosition, currentPosition, operator);
        }

        private boolean isOperator(char symbol) {
            return symbol == '-' || symbol == '+';
        }

        private boolean isExpectedSymbol(char symbol) {
            return Character.isDigit(symbol) || isOperator(symbol) || Character.isWhitespace(symbol);
        }

        private int convertToInt(int begin, int end, char operator) throws ParseException {

            if (begin == end) {
                return 0;
            }

            String token = expression.substring(begin, end).strip();

            try {
                int converted = Integer.parseInt(token);
                if (operator == '-') {
                    converted *= -1;
                }
                return converted;
            } catch (NumberFormatException e) {
                throw new ParseException("cant convert token \'" + token + "\' to int");
            }
        }
    }
}