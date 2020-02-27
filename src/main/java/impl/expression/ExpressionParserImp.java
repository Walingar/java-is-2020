package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImp implements ExpressionParser {


    public int parse(String s) throws ParseException {
        if (s == null) {
            throw new IllegalArgumentException("null input");
        }
        // adding more one '+' to avoid one more condition for the last number of the equation
        s = s + "+";
        int expressionLength = s.length();
        int temp = 0;
        int result = 0;
        char previousSign = '+';
        for (int i = 0; i < expressionLength; i++) {
            char currentSymbol = s.charAt(i);
            if (currentSymbol == ' ' || currentSymbol == '\t' || currentSymbol == '\n') {
            }
            else if (currentSymbol == '+') {
                // checking for the overflow
                try {
                    temp = ((previousSign == '+') ? 1 : -1) * temp;
                    int previousResult = result;
                    result = result + temp;
                    if (((previousResult ^ result) & (temp ^ result)) < 0) {
                        throw new ArithmeticException("OverFlow");
                    }
                    temp = 0;
                    previousSign = '+';
                } catch (ArithmeticException e) {
                    throw new ArithmeticException(" OverFlow in the result");
                }
            } else if (currentSymbol == '-') {
                // checking for the overflow
                temp = ((previousSign == '+') ? 1 : -1) * temp;
                int previousResult = result;
                result = result + temp;
                if (((previousResult ^ result) & (temp ^ result)) < 0) {
                    throw new ArithmeticException("OverFlow");
                }
                temp = 0;
                previousSign = '-';
            } else if (IsDigit(currentSymbol)) {
                try {
                    long x = temp;
                    temp = temp * 10;
                    long y = 10;
                    long ans = x * y;
                    // checking for the overflow
                    if ((int) ans != ans) {
                        System.out.print("Here an exception occurred ");
                        System.out.println((int) (ans));
                        throw new ArithmeticException("OverFlow");
                    }
                    int previousTemp = temp;
                    int currentDigit = currentSymbol - '0';
                    temp = temp + currentDigit;
                    // checking for the overflow
                    if (((previousTemp ^ temp) & (currentDigit ^ temp)) < 0) {
                        throw new ArithmeticException("OverFlow");
                    }
                } catch (ArithmeticException e) {
                    throw new ParseException("Large Numbers OverFlow Not Integers");
                }
            } else {
                throw new ParseException("Invalid Symbols");
            }
        }
        return result;
    }


    public boolean IsDigit(char c) {
        return (c - '0') >= 0 &&
                (c - '0') <= 9;
    }
}