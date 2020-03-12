package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Calculator implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression should not be null or empty");
        }
        var outputQueue = new LinkedList<String>();
        var operatorStack = new Stack<String>();
        int result = 0;
        //String[] tokens = expression.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
        var buffer = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            var curChar = expression.charAt(i);

            if (Character.isSpaceChar(curChar) || curChar == '\n' || curChar == '\t') {
                continue;
            }

            if (Character.isDigit(curChar) || i == 0) {
                buffer.append(curChar);
                continue;
            }

            if (isOperator(curChar)){
                result = getTempResult(buffer,result);
                buffer.setLength(0);
                buffer.append(curChar);
            }
        }
        result = getTempResult(buffer,result);

        return result;
    }

    private boolean isOperator(Character symbol) {
        return symbol == '+' || symbol == '-';
    }

    private int getTempResult(StringBuilder buffer,int result) throws ParseException{
        if(buffer.length() == 0){
            return 0;
        }
        int digit;
        try{
            digit = Integer.parseInt(buffer.toString());
            result += digit;
            return result;
        }catch(NumberFormatException ex) {
            throw new ParseException("Cant parse number to int");
        }
    }
}
