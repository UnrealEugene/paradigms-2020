package expression.generic;

public class ParserUnexpectedEndException extends ParserException {
    public ParserUnexpectedEndException(String message) {
        super(message);
    }

    public ParserUnexpectedEndException(String message, int position) {
        super(message, position);
    }
}
