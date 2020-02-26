package impl.expression;

import api.expression.ExpressionParser;
import api.expression.ParseException;
import impl.expression.lexis.LexemType;
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
        var lexem = lexer.getNextToken();
        while (lexem.getType() == LexemType.Operator) {
            var operator = (Operator) lexem;
            switch (operator.getOperatorType()) {
                case Plus: {
                    value = Math.addExact(value, parseTerm());
                    lexem = lexer.getNextToken();
                    break;
                }
                case Minus: {
                    value = Math.subtractExact(value, parseTerm());
                    lexem = lexer.getNextToken();
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
        var lexem = lexer.getNextToken();
//        Here should be switch to parse brackets and stuff, but ummm, we don't have it...
        if (lexem.getType() == LexemType.Number) {
            return ((Number) lexem).getValue();
        }
//        Unary minus support, it's just less code than switch for two cases
        if (lexem.getType() == LexemType.Operator &&
                ((Operator) lexem).getOperatorType() == OperatorType.Minus) {
            return -parseTerm();
        }
        throw new ParseException("Syntactycally invalid input!");
    }
}
