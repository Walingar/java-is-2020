package impl.expression.lexis;

public class Lexem {
    protected LexemType type;

    public Lexem(LexemType type) {
        this.type = type;
    }

    public LexemType getType() {
        return type;
    }
}
