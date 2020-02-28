package api.expression;

import api.expression.exception.ParseException;

public interface ExpressionParser {

  int parse(String expression) throws ParseException;
}