package impl.expression.lexer;

public class Token {
    public final String text;
    public final int position;
    public final TokenType tokenType;

    public Token(TokenType tokenType, String text, int position) {
        this.text = text;
        this.position = position;
        this.tokenType = tokenType;
    }
}
