package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserFactory {
    public static ExpressionParser getInstance(){
        ExpressionParser parser = (expression) -> { // Me gusta lambdas. Por favor, puedo use los) Can i use lambdas)
            try {
                var elements = expression.split("[ \\t\\r\\n]+");
                int previous = 0;
                int sign = 1;
                for (String element : elements) {
                    try {
                        int number = Integer.parseInt(element.strip());
                        if (Math.abs(Integer.MAX_VALUE-previous) < Math.abs(number)){
                            throw new ArithmeticException();
                        }
                        previous += sign * number;
                    } catch (NumberFormatException e) {
                        switch (element){
                            case "+":
                                sign = 1;
                                break;
                            case "-":
                                sign = -1;
                                break;
                            case "":
                                break;
                            default:
                                throw new ParseException("parse nonNumber");
                        }
                    }
                }
                return previous;
            } catch (NullPointerException e){
                throw new IllegalArgumentException(); // or better if (expression == null) throw new IllegalArgumentException(); ?????
            }
        };
        return parser;
    }
}