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
            return new Token(TokenType.MINUS, "-", position++);
        } else if (currentChar == '+') {
            return new Token(TokenType.PLUS, "+", position++);
        } else if (Character.isDigit(currentChar)) {
            return consumeIntegerConstant();
        }
        throw new ParseException("Unknown symbol \"" + currentChar + "\" at position " + position);
    }

    private Token consumeIntegerConstant() {
        int i = position;
        while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
            i++;
        }
        Token token = new Token(TokenType.INTEGER, expression.substring(position, i), position);
        position = i;
        return token;
    }
}
