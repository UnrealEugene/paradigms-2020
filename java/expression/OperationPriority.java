package expression;

public enum OperationPriority {
    LOWEST(Integer.MIN_VALUE), LOW(0), HIGH(1), HIGHER(2), UNARY(255), HIGHEST(Integer.MAX_VALUE);
    public int value;
    OperationPriority(int priority) {
        this.value = priority;
    }
}
