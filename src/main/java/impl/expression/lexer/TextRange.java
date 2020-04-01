package impl.expression.lexer;

public class TextRange {
    private final int start;
    private final int end;
    private final int length;

    private TextRange(int start, int end) {
        this.start = start;
        this.end = end;
        length = end - start;
    }

    public static TextRange of(int start, int end) {
        return new TextRange(start, end);
    }

    public static TextRange ofLength(int start, int length) {
        return new TextRange(start, start + length);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    public String substring(String string) {
        return length == 1 ?
                Character.toString(string.charAt(start))
                : string.substring(start, end);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", start, end);
    }
}
