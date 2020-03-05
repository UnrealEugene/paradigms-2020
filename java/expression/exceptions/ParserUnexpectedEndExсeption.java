package expression.exceptions;

public class ParserUnexpectedEndExсeption extends ParserException {
    public ParserUnexpectedEndExсeption(String message) {
        super(message);
    }

    public ParserUnexpectedEndExсeption(String message, int position) {
        super(message, position);
    }
}
