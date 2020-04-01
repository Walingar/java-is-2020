package impl.expression.lexer;

public class Token {
    private final String text;
    private final int position;
    private final TokenType tokenType;

    public Token(TokenType tokenType, String text, int position) {
        this.text = text;
        this.position = position;
        this.tokenType = tokenType;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
