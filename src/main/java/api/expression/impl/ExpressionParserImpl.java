package api.expression.impl;

import api.expression.ExpressionParser;
import api.expression.exception.ParseException;
import java.util.Objects;

public class ExpressionParserImpl implements ExpressionParser {

  private int expressionValue = 0;

  @Override
  public int parse(String expression) throws ParseException {
    expressionValue = 0;

    if (Objects.isNull(expression)) {
      throw new IllegalArgumentException("Expression must not be null!");
    }

    for (String expressionPart : parseExpression(expression)) {
      if (expressionPart.isBlank()) {
        continue;
      }

      updateExpressionValue(expressionPart);
    }

    return expressionValue;
  }

  private void updateExpressionValue(String number) throws ParseException {
    try {
      expressionValue = Math.addExact(expressionValue, Integer.parseInt(number));
    } catch (NumberFormatException exception) {
      throw new ParseException(exception.getMessage(), exception);
    }
  }

  private String[] parseExpression(String expression) {
    return expression
        .replaceAll("\\s+", "")
        .replace("-", "+-")
        .split("\\+");
  }
}