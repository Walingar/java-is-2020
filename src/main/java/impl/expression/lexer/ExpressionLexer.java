package impl.expression.lexer;

import api.expression.ParseException;

public class ExpressionLexer {
    private final String expression;
    private int position = 0;

    public ExpressionLexer(String expression) {
        this.expression = expression;
        skipWhitespaces();
    }

    public boolean hasNext() {
        return position != expression.length();
    }

    public Token nextToken() throws ParseException {
        if (!hasNext()) {
            throw new ParseException("Expression ends unexpectedly");
        }
        Token result = consumeNextToken();
        skipWhitespaces();
        return result;
    }

    private void skipWhitespaces() {
        int i = position;
        while (i < expression.length() && Character.isWhitespace(expression.charAt(i))) {
            i++;
        }
        position = i;
    }

    private Token consumeNextToken() throws ParseException  {
        char currentChar = expression.charAt(position);
        if (currentChar == '-') {
            return new Token(TokenType.MINUS, expression, TextRange.ofLength(position++, 1));
        } else if (currentChar == '+') {
            return new Token(TokenType.PLUS, expression, TextRange.ofLength(position++, 1));
        } else if (Character.isDigit(currentChar)) {
            return consumeIntegerConstant();
        }
        throw new ParseException(String.format("Unknown symbol \"%s\" at position %d", currentChar, position));
    }

    private Token consumeIntegerConstant() {
        int i = position;
        while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
            i++;
        }
        Token token = new Token(TokenType.INTEGER, expression, TextRange.of(position, i));
        position = i;
        return token;
    }
}
