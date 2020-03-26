package expression.exceptions;

public class ExpressionDBZException extends ExpressionCalculateException {
    public ExpressionDBZException(String message) {
        super(message);
    }

    public ExpressionDBZException(String message, RuntimeException e) {
        super(message, e);
    }
}
