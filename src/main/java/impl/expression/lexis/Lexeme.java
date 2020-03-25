package impl.expression.lexis;

public class Lexeme {
    protected final LexemeType type;

    public Lexeme(LexemeType type) {
        this.type = type;
    }

    public LexemeType getType() {
        return type;
    }
}
