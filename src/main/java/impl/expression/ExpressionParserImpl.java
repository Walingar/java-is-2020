package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;
import impl.expression.lexer.ExpressionLexer;
import impl.expression.lexer.TextRange;
import impl.expression.lexer.Token;

import java.util.function.BinaryOperator;

/**
 * ExpressionParser for parsing the following grammar
 * <pre>
 * Grammar:
 *   expression ::= first_term term*
 *   first_term ::= sign? integer
 *   term ::= sign integer
 *   sign ::= '+' | '-'
 *   integer ::= ['0'..'9']+
 * </pre>
 */
public class ExpressionParserImpl implements ExpressionParser {
    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        return parseExpression(new ExpressionLexer(expression));
    }

    private static int parseExpression(ExpressionLexer tokenizer) throws ParseException {
        int result = parseFirstTerm(tokenizer);
        while (tokenizer.hasNext()) {
            BinaryOperator<Integer> operation = getOperation(tokenizer.nextToken());
            int rightOperand = parseInteger(tokenizer.nextToken());
            result = operation.apply(result, rightOperand);
        }
        return result;
    }

    private static int parseFirstTerm(ExpressionLexer tokenizer) throws ParseException {
        Token token = tokenizer.nextToken();
        switch (token.getTokenType()) {
            case PLUS:
                return parseInteger(tokenizer.nextToken());
            case MINUS:
                return -parseInteger(tokenizer.nextToken());
            default:
                return parseInteger(token);
        }
    }

    private static int parseInteger(Token token) throws ParseException {
        TextRange tokenTextRange = token.getTextRange();
        if (10 < tokenTextRange.getLength()) {
            throw new ParseException("Token is too big for an integer at position " + tokenTextRange);
        }
        try {
            return Integer.parseInt(token.getText());
        } catch (NumberFormatException ex) {
            throw createUnexpectedTokenException("Integer constant", token);
        }
    }

    private static BinaryOperator<Integer> getOperation(Token token) throws ParseException {
        switch (token.getTokenType()) {
            case PLUS:
                return Math::addExact;
            case MINUS:
                return Math::subtractExact;
            default:
                throw createUnexpectedTokenException("Operation", token);
        }
    }

    private static ParseException createUnexpectedTokenException(String expected, Token token) {
        return new ParseException(
                String.format("%s was expected, but found \"%s\" at position %s", expected, token.getText(),
                              token.getTextRange()));
    }
}
