package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;

public class ExpressionParserFactory {
    public static ExpressionParser getInstance(){
        return new ExpressionParserImpl();
    }
}