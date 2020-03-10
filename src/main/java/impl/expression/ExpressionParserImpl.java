package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;


import java.util.ArrayList;

public class ExpressionParserImpl implements ExpressionParser {

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is empty");
        }
        int calcResult = 0;
        expression = expression.replaceAll("\\s+", "");
        expression += '+';
        char[] expElements = expression.toCharArray();
        if (expElements[0] == '-') {
            char[] buffer = new char[expElements.length + 1];
            buffer[0] = '0';
            for (int i = 0; i < expElements.length; i++) {
                buffer[i + 1] = expElements[i];
            }
            expElements = buffer;
        }
        ArrayList<String> operators = new ArrayList<>();
        ArrayList<String> operands = new ArrayList<>();
        String buffer = "";
        for (char element : expElements) {
            if (element == '-'){
                operands.add(buffer);
                operators.add(element+"");
                buffer = "";
            } else if (element == '+') {
                operands.add(buffer);
                operators.add(element+"");
                buffer = "";
            } else {
                buffer += element;
            }
        }
        if (operators.size() > 0){
            operators.remove(operators.size()-1);
        }
        ExpressionParserImpl calculator = new ExpressionParserImpl();
        calcResult = calculator.calcResult(operators, operands);

        return calcResult;
    }

    private int calcResult(ArrayList<String> operators, ArrayList<String> operands) throws ParseException {
        try {
            int result = Integer.parseInt(operands.get(0));
            for (int i = 0; i < operators.size(); i++) {
                if (operators.get(i).equals("+")) {
                    result += Integer.parseInt(operands.get(i + 1));
                } else if (operators.get(i).equals("-")) {
                    result -= Integer.parseInt(operands.get(i + 1));
                }
            }
            return result;
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }

    }
}
