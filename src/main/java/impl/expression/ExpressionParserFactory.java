package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;



public class ExpressionParserFactory {
    public static ExpressionParser getInstance() {
        ExpressionParser expressionParser = new ExpressionParser(){

            @Override
            public int parse(String expression) throws ParseException {
                if(expression == null){
                    throw new IllegalArgumentException("expression is null");
                }
                String[] decima = expression.split("[\\t\\n ]+");
                int sum = 0;
                int k = 1;
                for (int i = 0; i<decima.length; i++){
                    try{
                        if (decima[i].equals("+")){
                            k = 1;
                            continue;
                        }
                        if (decima[i].equals("-")){
                            k = -1;
                            continue;
                        }
                        if (decima[i].isEmpty()){
                            continue;
                        }

                        int temp =  Integer.parseInt(decima[i]);
                        if (Math.abs(temp) >= Math.abs(Integer.MAX_VALUE - sum)){
                            throw new ArithmeticException("overflow");
                        }
                        sum = sum + k*temp;

                    } catch (NumberFormatException e){
                        throw new ParseException("long type");
                    }

                }
                return sum;
            }
        };
        return expressionParser;
    }
}