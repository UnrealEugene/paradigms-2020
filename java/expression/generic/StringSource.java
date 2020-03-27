package expression.generic;

public class StringSource {
    private String source;
    private int currentPos;

    public StringSource(String source) {
        this.source = source;
        currentPos = 0;
    }

    public char get(int pos) {
        return source.charAt(pos);
    }

    public char current() {
        return get(currentPos);
    }

    public char next() {
        return get(currentPos + 1);
    }

    public char prev() {
        return get(currentPos - 1);
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int getSize() {
        return source.length();
    }

    public void moveNext() {
        currentPos++;
    }

    public void move(int offset) {
        currentPos += offset;
    }

    public boolean isEnd() {
        return currentPos >= source.length();
    }
}
