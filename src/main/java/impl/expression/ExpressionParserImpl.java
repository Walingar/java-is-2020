package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

import java.util.ArrayList;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null)
            throw new IllegalArgumentException();

        String expressionWithoutEmptySpaces = GetStringWithoutEmptySpace(expression);

        String[] operations = GetOperations(expressionWithoutEmptySpaces);
        String[] values = GetValues(expressionWithoutEmptySpaces);

        int numberOfValues = values.length;
        int numberOfOperations = operations.length;

        if (!IsOperationsAndValuesMatch(numberOfValues, numberOfOperations))
            throw new ParseException("Too much operations!");

        if (!CanParseValues(values))
            throw new ParseException("Can't parse values!");

        if (!CanParseOperations(operations))
            throw new ParseException("Can't parse operations!");

        int[] parsedValues = ParseValues(values);

        boolean firstValueHaveSign = numberOfOperations == numberOfValues;
        int result = GetFirstValue(parsedValues[0], firstValueHaveSign, operations);

        for (int valueNumber = 1; valueNumber < parsedValues.length; valueNumber++) {
            String operation = GetCurrentOperation(operations, valueNumber, firstValueHaveSign);
            if (operation.equals("+"))
                result = Math.addExact(result, parsedValues[valueNumber]);
            else
                result = Math.subtractExact(result, parsedValues[valueNumber]);
        }

        return result;
    }

    private String GetCurrentOperation(String[] operations, int valueNumber, boolean firstValueHaveSign) {
        if (firstValueHaveSign)
            return operations[valueNumber];
        else
            return operations[valueNumber - 1];
    }

    private int GetFirstValue(int firstValue, boolean firstValueHaveSign, String[] operations) {
        int result = firstValue;

        if (firstValueHaveSign && operations[0].equals("-"))
            result *= -1;

        return result;
    }

    private boolean CanParseOperations(String[] operations) {
        for (int i = 1; i < operations.length; i++) {
            if (!operations[i].equals("+") && !operations[i].equals("-"))
                return false;
        }

        return true;
    }

    private boolean CanParseValues(String[] values) {
        for (String value : values) {
            if (!CanParseInt(value))
                return false;
        }

        return true;
    }

    private int[] ParseValues(String[] values) {
        int[] parsedValues = new int[values.length];
        for (int i = 0; i < values.length; i++) {
                parsedValues[i] = Integer.parseInt(values[i]);
            }

        return parsedValues;
    }

    private boolean IsOperationsAndValuesMatch(int numberOfValues, int numberOfOperations) {
        return numberOfOperations == numberOfValues || numberOfOperations == numberOfValues - 1;
    }

    private boolean CanParseInt(String integer) {
        try {
            Integer.parseInt(integer);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean IsFirstNumberHaveSign(String firstOperation) {
        return firstOperation.equals("+") || firstOperation.equals("-");
    }

    private String[] GetValues(String source) {
        String[] values = source.split("[+-]");
        return RemoveEmptyStrings(values);
    }

    private String[] GetOperations(String source) {
        String[] operations = source.split("[\\d]+");
        return RemoveEmptyStrings(operations);
    }

    private String[] RemoveEmptyStrings(String[] strings) {
        ArrayList<String> result = new ArrayList<>();
        for (String string : strings) {
            if (!string.isEmpty())
                result.add(string);
        }

        String[] resultArray = new String[result.size()];
        result.toArray(resultArray);

        return resultArray;
    }

    private String GetStringWithoutEmptySpace(String source) {
        String[] substrings = source.split("[\\s]+");

        StringBuilder stringBuilder = new StringBuilder();
        for (String substring : substrings) {
            if (substring.isEmpty() || substring.isBlank())
                continue
            ;
            stringBuilder.append(substring);
        }

        return stringBuilder.toString();
    }
}
