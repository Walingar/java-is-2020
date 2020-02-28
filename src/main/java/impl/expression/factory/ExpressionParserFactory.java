package impl.expression.factory;

import api.expression.ExpressionParser;
import api.expression.impl.ExpressionParserImpl;

public class ExpressionParserFactory {
    public static ExpressionParser getInstance() {
        return new ExpressionParserImpl();
    }
}