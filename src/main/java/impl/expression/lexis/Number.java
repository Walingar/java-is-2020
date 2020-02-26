package impl.expression.lexis;

public class Number extends Lexem {

    private int value;

    public Number(int value) {
        super(LexemType.Number);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
