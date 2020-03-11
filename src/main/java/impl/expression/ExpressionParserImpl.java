package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException, ArithmeticException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        expression = expression.replaceAll("\\s", "");
        if (expression.isBlank()) {
            throw new IllegalArgumentException("Expression is empty");
        }
        int result = 0;
        StringBuilder currentNumber = new StringBuilder();
        //проход по строке
        for (int index = 0; index < expression.length(); index++) {
            char currentChar = expression.charAt(index);
            // Дописываем цифру в строку
            if (Character.isDigit(currentChar)) {
                currentNumber.append(currentChar);
                continue;
            }
            // если знак
            if (currentChar == '+' || currentChar == '-') {
                if(currentNumber.length() != 0){
                    result = Math.addExact(result, getNumber(currentNumber));
                    currentNumber = new StringBuilder();
                }
                currentNumber.append(currentChar);
                continue;
            }
            throw new ParseException(String.format("Illegal character '%c' ", currentChar));
        }
        if (currentNumber.length() != 0) {
            result = Math.addExact(result, getNumber(currentNumber));
        }
        return result;
    }

    private int getNumber(StringBuilder currentNumber) throws ParseException {
        try {
            return Integer.parseInt(currentNumber.toString());
        } catch (NumberFormatException e){
            throw new ParseException("Not valid integer format");
        }
    }
}