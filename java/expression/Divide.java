package expression;

import java.util.Map;

public class Divide extends BinaryOperation implements MultipleExpression {

    public Divide(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("/");
    }

    @Override
    public String toMiniString() {
        return super.toMiniString("/");
    }

    @Override
    protected int calculate(int left, int right) { return left / right; }

    @Override
    public int hashCode() {
        return super.hashCode("/");
    }

    @Override
    public boolean isAssociative() {
        return false;
    }

    @Override
    public OperationPriority getPriority() {
        return OperationPriority.HIGH;
    }
}
