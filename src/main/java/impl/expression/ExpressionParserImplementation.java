package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserImplementation implements ExpressionParser {
    public int parse(String expression) throws ParseException {
        int result = 0;
        StringBuilder bufString = new StringBuilder();

        if (expression == null) {
            throw new IllegalArgumentException("NULL expression");
        }


        if (expression.isBlank()) {
            throw new IllegalArgumentException("Blank expression");
        }

        if(!expression.contains("+")&&!expression.contains("-") )
        {
            try {
                result = Math.addExact(result, Integer.parseInt(expression.toString()));
                return  result;
            }
            catch (NumberFormatException a) {
                throw new ParseException("Incorrect format");
            }

        }
        for (int i = 0; i < expression.length(); i++) {
            char bufSymbol = expression.charAt(i);

            if (Character.isDigit(bufSymbol)) {
                bufString.append(bufSymbol);
            }



           //"1 + 12 + 3 + 564 + 123"
            if (bufSymbol == '+' || bufSymbol == '-'||i==(expression.length()-1)) {
                if (bufString.length() != 0) {
                    try {
                        result = Math.addExact(result, Integer.parseInt(bufString.toString()));
                        bufString = new StringBuilder();
                    } catch (NumberFormatException a) {
                        throw new ParseException("Incorrect format");
                    }
                }
                bufString.append(bufSymbol);
            }

        }
        return result;
    }
}