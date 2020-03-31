package impl.expression;

import api.expression.ExpressionParser;

public class ExpressionParserFactory {
    public static ExpressionParser getInstance() {
        return new ExpressionParserImpl();
    }
}