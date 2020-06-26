package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {
    private static final char ADD = '+';
    private static final char SUBTRACT = '-';

    private static final int NO_INDEX = -1;

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException();
        }

        int calculationResult = 0;
        char currentOperation = ADD;

        int numberStartIndex = NO_INDEX;
        int numberEndIndex = NO_INDEX;

        try {
            for (int index = 0; index < expression.length(); index++) {
                char currentSymbol = expression.charAt(index);

                if (Character.isWhitespace(currentSymbol)) {
                    continue;
                }

                if (currentSymbol == ADD) {
                    calculationResult = tryDoOperation(calculationResult, currentOperation, expression,
                            numberStartIndex, numberEndIndex);

                    currentOperation = ADD;

                    numberStartIndex = NO_INDEX;
                    numberEndIndex = NO_INDEX;
                } else if (currentSymbol == SUBTRACT) {
                    calculationResult = tryDoOperation(calculationResult, currentOperation, expression,
                            numberStartIndex, numberEndIndex);

                    currentOperation = SUBTRACT;

                    numberStartIndex = NO_INDEX;
                    numberEndIndex = NO_INDEX;
                } else if (Character.isDigit(currentSymbol)) {
                    if (numberStartIndex == NO_INDEX) {
                        numberStartIndex = index;
                    }

                    numberEndIndex = index + 1;
                } else {
                    throw new ParseException("Cannot parse character");
                }
            }

            calculationResult = tryDoOperation(calculationResult, currentOperation, expression, numberStartIndex,
                    numberEndIndex);
        } catch (NumberFormatException ex) {
            throw new ParseException("Number format exception");
        }

        return calculationResult;
    }

    private int tryDoOperation(int total, char operation, String expression,
                               int startPosition, int endPosition) {
        int value = getInt(expression, startPosition, endPosition);
        if (operation == ADD) {
            return Math.addExact(total, value);
        } else {
            return Math.subtractExact(total, value);
        }
    }

    private int getInt(String expression, int startPosition, int endPosition) {
        if (startPosition == NO_INDEX) {
            return 0;
        }

        String number = expression.substring(startPosition, endPosition);
        return Integer.parseInt(number);
    }
}
