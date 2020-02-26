package impl.expression.lexis;

public class Operator extends Lexem {

    private OperatorType type;

    public Operator(OperatorType type) {
        super(LexemType.Operator);
        this.type = type;
    }

    public OperatorType getOperatorType() {
        return type;
    }
}
