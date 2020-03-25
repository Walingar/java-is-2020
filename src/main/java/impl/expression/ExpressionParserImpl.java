package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;
import impl.expression.lexis.LexemeType;
import impl.expression.lexis.Operator;
import impl.expression.lexis.Number;
import impl.expression.lexis.OperatorType;

public class ExpressionParserImpl implements ExpressionParser {

    private Lexer lexer;

    @Override
    public int parse(String expression) throws ParseException {
        if (expression == null) {
            throw new IllegalArgumentException("Null string passed for parsing");
        }
//        Maybe rework these deps somehow, pass as argument or something like that...
        lexer = new Lexer(expression);
        return parseExpression();
    }

    private int parseExpression() throws ParseException {
//        well, generally we'd want to build AST first and eval later, but, well, yeah...
        var value = parseTerm();
        var lexeme = lexer.getNextToken();
        while (lexeme.getType() == LexemeType.OPERATOR) {
            var operator = (Operator) lexeme;
            switch (operator.getOperatorType()) {
                case PLUS: {
                    value = Math.addExact(value, parseTerm());
                    lexeme = lexer.getNextToken();
                    break;
                }
                case MINUS: {
                    value = Math.subtractExact(value, parseTerm());
                    lexeme = lexer.getNextToken();
                    break;
                }
                default: {
                    throw new ParseException("Syntactically invalid input! ");
                }
            }
        }
        return value;
    }

    private int parseTerm() throws ParseException {
        var lexeme = lexer.getNextToken();
//        Here should be switch to parse brackets and stuff, but ummm, we don't have it...
        if (lexeme.getType() == LexemeType.NUMBER) {
            return ((Number) lexeme).getValue();
        }
//        Unary minus support, it's just less code than switch for two cases
        if (lexeme.getType() == LexemeType.OPERATOR &&
                ((Operator) lexeme).getOperatorType() == OperatorType.MINUS) {
            return -parseTerm();
        }
        throw new ParseException("Syntactycally invalid input!");
    }
}
