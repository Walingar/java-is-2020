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
        private boolean eof;
        private int currentPosition;
        private boolean breakOnSign;

        IntegerParser(String expression) throws IllegalArgumentException {
            if (expression == null) {
                throw new IllegalArgumentException("expected string, got null");
            }
            this.expression = expression;
            eof = false;
            breakOnSign = false;
            currentPosition = 0;
        }

        boolean isEof() {
            return eof;
        }

        private void metEof() {
            eof = true;
        }

        boolean hasNextInt() {
            return !isEof();
        }

        int nextInt() throws ParseException {

            StringBuilder stringBuilder = new StringBuilder();

            if (isEof()) {
                throw new ParseException("unexpected end of string");
            }

            if (breakOnSign) {
                stringBuilder.append(expression.charAt(currentPosition));
                currentPosition++;
                breakOnSign = false;
            }

            while (currentPosition < expression.length()) {
                char currentCharater = expression.charAt(currentPosition);

                if (Character.isWhitespace(currentCharater)) {
                    currentPosition++;
                    continue;
                }

                if (!isExpectedSymbol(currentCharater)) {
                    throw new ParseException("unexpected symbol");
                }

                if (isArithmeticSymbol(currentCharater)) {
                    breakOnSign = true;
                    break;
                }

                currentPosition++;
                stringBuilder.append(currentCharater);
            }

            if (currentPosition == expression.length()) {
                metEof();
            }

            return convertToInt(stringBuilder);
        }

        private boolean isArithmeticSymbol(char symbol) {
            return symbol == '-' || symbol == '+';
        }

        private boolean isExpectedSymbol(char symbol) {
            return Character.isDigit(symbol) || isArithmeticSymbol(symbol);
        }

        private int convertToInt(StringBuilder stringBuilder) throws ParseException {
            if (stringBuilder.length() == 0) {
                return 0;
            }
            try {
                return Integer.parseInt(stringBuilder.toString());
            } catch (NumberFormatException e) {
                throw new ParseException("cant convert token to int");
            }
        }
    }
}