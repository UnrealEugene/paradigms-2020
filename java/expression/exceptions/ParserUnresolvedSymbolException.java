package expression.exceptions;

public class ParserUnresolvedSymbolException extends ParserException {
    public ParserUnresolvedSymbolException(String message) {
        super(message);
    }

    public ParserUnresolvedSymbolException(String message, int position) {
        super(message, position);
    }
}
