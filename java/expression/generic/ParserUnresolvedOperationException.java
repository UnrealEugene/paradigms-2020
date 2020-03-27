package expression.generic;

public class ParserUnresolvedOperationException extends ParserException {
    public ParserUnresolvedOperationException(String message) {
        super(message);
    }

    public ParserUnresolvedOperationException(String message, int position) {
        super(message, position);
    }
}
