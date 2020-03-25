package impl.expression.lexis;

public class Number extends Lexeme {

    private final int value;

    public Number(int value) {
        super(LexemeType.NUMBER);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
