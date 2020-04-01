package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImp implements ExpressionParser {


    public int parse(String s) throws ParseException {
        if (s == null) {
            throw new IllegalArgumentException("null input");
        }
        int expressionLength = s.length();
        int currentNumber = 0;
        int result = 0;
        char previousSign = '+';
        for (int i = 0; i < expressionLength; i++) {
            char currentSymbol = s.charAt(i);
            if (currentSymbol == '-' || currentSymbol == '+') {
                currentNumber = ((previousSign == '-') ? -1 : 1) * currentNumber;
                result = Math.addExact(result, currentNumber);
                currentNumber = 0;
                previousSign = currentSymbol;
            } else if (Character.isDigit(currentSymbol)) {
                try {
                    currentNumber = Math.addExact(Math.multiplyExact(currentNumber, 10), Character.getNumericValue(currentSymbol));
                } catch (ArithmeticException ex) {
                    throw new ParseException("OverFlow");
                }
            } else if (!Character.isWhitespace(currentSymbol) && currentSymbol != '\t' && currentSymbol != '\n') {
                throw new ParseException("Invalid Symbols");
            }
        }
        return Math.addExact(result, ((previousSign == '-') ? -1 : 1) * currentNumber);
    }


}