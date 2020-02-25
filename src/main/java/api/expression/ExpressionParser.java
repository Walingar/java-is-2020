package api.expression;

public interface ExpressionParser {
    int parse(String expression) throws ParseException;
}