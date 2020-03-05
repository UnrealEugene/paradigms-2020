package expression.parser;

public abstract class BaseParser implements Parser {
    protected StringSource currentSource = null;

    protected void skipWhitespaces() {
        while (!currentSource.isEnd() && Character.isWhitespace(currentSource.current())) {
            currentSource.moveNext();
        }
    }

    protected boolean check(char c, boolean skipWhitespaces) {
        if (skipWhitespaces) {
            skipWhitespaces();
        }
        if (currentSource.isEnd()) {
            return false;
        }
        return currentSource.current() == c;
    }

    protected boolean check(char c) {
        return check(c, true);
    }

    protected boolean checkBetween(char from, char to) {
        if (currentSource.isEnd()) {
            return false;
        }
        return from <= currentSource.current() && currentSource.current() <= to;
    }

    protected boolean checkAndMove(char c, boolean skipWhitespaces) {
        if (check(c, skipWhitespaces)) {
            currentSource.moveNext();
            return true;
        }
        return false;
    }

    protected boolean checkAndMove(char c) {
        return checkAndMove(c, true);
    }

    protected boolean checkAndMove(String str) {
        skipWhitespaces();
        for (int i = 0; i < str.length(); i++) {
            if (!checkAndMove(str.charAt(i), false)) {
                currentSource.move(-i);
                return false;
            }
        }
        return true;
    }

    protected void expect(char c) {
        if (!check(c)) {
            /// exception
            return;
        }
        currentSource.moveNext();
    }

    protected void expect(String val) {
        for (char c : val.toCharArray()) {
            expect(c);
        }
    }
}
