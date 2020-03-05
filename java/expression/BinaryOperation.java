package expression;

import java.util.Map;
import java.util.Objects;

public abstract class BinaryOperation implements MultipleExpression {
    protected final MultipleExpression left, right;

    protected BinaryOperation(MultipleExpression left, MultipleExpression right) {
        this.left = left;
        this.right = right;
    }

    protected String toMiniString(String opString) {
        boolean needLeftBraces, needRightBraces;
        needLeftBraces = this.getPriority().value > left.getPriority().value;
        needRightBraces = !(this.getPriority().value < right.getPriority().value ||
                this.getClass() == right.getClass() && this.isAssociative());
        return put(needLeftBraces, left) + " " + opString + " " + put(needRightBraces, right);
    }

    private String put(boolean needBraces, MultipleExpression op) {
        return (needBraces ? "(" : "") + op.toMiniString() + (needBraces ? ")" : "");
    }

    protected String toString(String opString) {
        return "(" + left + " " + opString + " " + right + ")";
    }

    @Override
    public int evaluate(Map<String, Integer> vars) {
        return calculate(left.evaluate(vars), right.evaluate(vars));
    }

    protected abstract int calculate(int left, int right);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        BinaryOperation that = (BinaryOperation) obj;
        return left.equals(that.left) && right.equals(that.right);
    }

    public int hashCode(String opString) {
        return Objects.hash(left, right, getClass());
    }
}
