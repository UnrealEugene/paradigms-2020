package expression.generic;

public class ExpressionCalculateException extends RuntimeException {
    public ExpressionCalculateException(String message) {
        super(message);
    }

    public ExpressionCalculateException(String message, RuntimeException cause) {
        super(message, cause);
    }
}
