package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

import java.util.ArrayList;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException();
        }

        String expressionWithoutEmptySpaces = getStringWithoutEmptySpace(expression);

        String[] operations = getOperations(expressionWithoutEmptySpaces);
        String[] values = getValues(expressionWithoutEmptySpaces);

        int numberOfValues = values.length;
        int numberOfOperations = operations.length;

        if (!isOperationsAndValuesMatch(numberOfValues, numberOfOperations)) {
            throw new ParseException("Too much operations!");
        }

        if (!canParseValues(values)) {
            throw new ParseException("Can't parse values!");
        }

        if (!canParseOperations(operations)) {
            throw new ParseException("Can't parse operations!");
        }

        int[] parsedValues = parseValues(values);

        boolean firstValueHaveSign = numberOfOperations == numberOfValues;
        int result = getFirstValue(parsedValues[0], firstValueHaveSign, operations);

        for (int valueNumber = 1; valueNumber < parsedValues.length; valueNumber++) {
            String operation = getCurrentOperation(operations, valueNumber, firstValueHaveSign);
            if (operation.equals("+")) {
                result = Math.addExact(result, parsedValues[valueNumber]);
            } else {
                result = Math.subtractExact(result, parsedValues[valueNumber]);
            }
        }

        return result;
    }

    private String getCurrentOperation(String[] operations, int valueNumber, boolean firstValueHaveSign) {
        if (firstValueHaveSign) {
            return operations[valueNumber];
        } else {
            return operations[valueNumber - 1];
        }
    }

    private int getFirstValue(int firstValue, boolean firstValueHaveSign, String[] operations) {
        int result = firstValue;

        if (firstValueHaveSign && operations[0].equals("-")) {
            result *= -1;
        }

        return result;
    }

    private boolean canParseOperations(String[] operations) {
        for (int i = 1; i < operations.length; i++) {
            if (!operations[i].equals("+") && !operations[i].equals("-")) {
                return false;
            }
        }

        return true;
    }

    private boolean canParseValues(String[] values) {
        for (String value : values) {
            if (!canParseInt(value)) {
                return false;
            }
        }

        return true;
    }

    private int[] parseValues(String[] values) {
        int[] parsedValues = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            parsedValues[i] = Integer.parseInt(values[i]);
        }

        return parsedValues;
    }

    private boolean isOperationsAndValuesMatch(int numberOfValues, int numberOfOperations) {
        return numberOfOperations == numberOfValues || numberOfOperations == numberOfValues - 1;
    }

    private boolean canParseInt(String integer) {
        try {
            Integer.parseInt(integer);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private String[] getValues(String source) {
        String[] values = source.split("[+-]");
        return removeEmptyStrings(values);
    }

    private String[] getOperations(String source) {
        String[] operations = source.split("[\\d]+");
        return removeEmptyStrings(operations);
    }

    private String[] removeEmptyStrings(String[] strings) {
        ArrayList<String> result = new ArrayList<>();
        for (String string : strings) {
            if (!string.isEmpty()) {
                result.add(string);
            }
        }

        String[] resultArray = new String[result.size()];
        result.toArray(resultArray);

        return resultArray;
    }

    private String getStringWithoutEmptySpace(String source) {
        String[] substrings = source.split("[\\s]+");

        StringBuilder stringBuilder = new StringBuilder();
        for (String substring : substrings) {
            if (substring.isEmpty() || substring.isBlank()) {
                continue;
            }
            stringBuilder.append(substring);
        }

        return stringBuilder.toString();
    }
}
