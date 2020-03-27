package expression.generic;

public class ParserException extends RuntimeException {
    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message, int position) {
        super(message + " (position " + position + ")");
    }

    public ParserException(String message, Throwable cause, int position) {
        super(message + " (position " + position + ")", cause);
    }
}
