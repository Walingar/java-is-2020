package api.expression.impl;

import api.expression.ExpressionParser;
import api.expression.exception.ParseException;
import java.util.Objects;

public class ExpressionParserImpl implements ExpressionParser {

  private int expressionValue = 0;
  private int sign = 1;
  private int currentNumber = 0;

  @Override
  public int parse(String expression) throws ParseException {
    setUp();

    if (Objects.isNull(expression)) {
      throw new IllegalArgumentException("Expression must not be null!");
    }

    for (char expressionSymbol : expression.toCharArray()) {
      if (Character.isDigit(expressionSymbol)) {
        addPartOfNumber(expressionSymbol);
        continue;
      }

      if (isSign(expressionSymbol)) {
        updateExpressionValue();
        changeSign(expressionSymbol);
        continue;
      }

      if (!Character.isWhitespace(expressionSymbol)) {
        throw new ParseException("Not a valid expression symbol found!");
      }
    }

    updateExpressionValue();
    return expressionValue;
  }

  private void setUp() {
    expressionValue = 0;
    currentNumber = 0;
    sign = 1;
  }

  private void changeSign(char expressionSymbol) {
    sign = (expressionSymbol == '+') ? 1 : -1;
  }

  private boolean isSign(char expressionSymbol) {
    return expressionSymbol == '+' || expressionSymbol == '-';
  }

  private void updateExpressionValue() {
    expressionValue = Math.addExact(expressionValue, sign * currentNumber);
    currentNumber = 0;
  }

  private void addPartOfNumber(char symbol) throws ParseException {
    try {
      currentNumber = Math
          .addExact(Math.multiplyExact(currentNumber, 10), Character.getNumericValue(symbol));
    } catch (ArithmeticException exception) {
      throw new ParseException(exception.getMessage(), exception);
    }
  }
}