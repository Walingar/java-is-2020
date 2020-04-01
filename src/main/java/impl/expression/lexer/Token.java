package impl.expression.lexer;

public class Token {
    private final String expressionText;
    private final TextRange textRange;
    private final TokenType tokenType;
    private String lazyActualText;

    public Token(TokenType tokenType, String text, TextRange textRange) {
        this.expressionText = text;
        this.tokenType = tokenType;
        this.textRange = textRange;
    }

    public String getText() {
        if (lazyActualText != null) {
            return lazyActualText;
        }
        lazyActualText = textRange.substring(expressionText);
        return lazyActualText;
    }

    public TextRange getTextRange() {
        return textRange;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
