package impl.expression.lexis;

public class Operator extends Lexeme {

    private final OperatorType type;

    public Operator(OperatorType type) {
        super(LexemeType.OPERATOR);
        this.type = type;
    }

    public OperatorType getOperatorType() {
        return type;
    }
}
