package expression;

public interface AdvancedExpression {
    default boolean isAssociative() {
        return false;
    }

    default OperationPriority getPriority() {
        return OperationPriority.HIGHEST;
    }
}
