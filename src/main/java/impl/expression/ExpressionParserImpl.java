package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        expression = expression.replaceAll(" ", "");
        int result = 0;
        char sign = '.';
        StringBuilder currentNumber = new StringBuilder();
        //проход по строке
        for (int index = 0; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            // Дописываем цифру в строку
            if ('0' <= currentChar && currentChar <= '9') {
                currentNumber.append(currentChar);
                continue;
            }
            // если знак
            if (currentChar == '+' || currentChar == '-') {
                if (sign == '.') {
                    result += getNumber(currentNumber, '+');
                }
                if (sign == '+' || sign == '-') {
                    result += getNumber(currentNumber, sign);
                }
                sign = currentChar;
                currentNumber = new StringBuilder();
                continue;
            }
            // если конец строки
            if (index == expression.length() - 1) {
                result += getNumber(currentNumber, sign);
                currentNumber = new StringBuilder();
                continue;
            }
            throw new ParseException(String.format("Illegal character '%c' ", currentChar));
        }
        return result;
    }

    private int getNumber(StringBuilder currentNumber, char sign) throws ParseException {
        if (currentNumber == null || currentNumber.length() == 0) {
            throw new ParseException("More than one sign in a row are not permitted");
        }
        int current = Integer.parseInt(currentNumber.toString());
        return '-' == sign ? -current : current;
    }
}