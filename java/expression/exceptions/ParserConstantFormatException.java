package expression.exceptions;

public class ParserConstantFormatException extends ParserException {
    public ParserConstantFormatException(String message, NumberFormatException e, int i) {
        super(message);
    }

    public ParserConstantFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
