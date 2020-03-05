package expression;

import java.util.Map;
import java.util.Objects;

public abstract class UnaryOperation implements MultipleExpression {
    protected final MultipleExpression arg;

    protected UnaryOperation(MultipleExpression arg) {
        this.arg = arg;
    }

    public OperationPriority getPriority() {
        return OperationPriority.UNARY;
    }

    protected String toMiniString(String opString) {
        boolean needBraces = this.getPriority().value > arg.getPriority().value;
        return opString + (needBraces ? "(" : " ") + arg.toMiniString() + (needBraces ? ")" : "");
    }

    protected String toString(String opString) {
        return opString + "(" + arg.toMiniString() + ")";
    }

    @Override
    public int evaluate(Map<String, Integer> vars) {
        return calculate(arg.evaluate(vars));
    }

    protected abstract int calculate(int arg);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        UnaryOperation that = (UnaryOperation) obj;
        return arg.equals(that.arg);
    }

    public int hashCode(String opString) {
        return Objects.hash(arg, getClass());
    }
}
