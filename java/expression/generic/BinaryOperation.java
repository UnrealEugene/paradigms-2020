package expression.generic;

import java.util.Map;

public abstract class BinaryOperation <T extends MyNumber<T>> implements MultipleExpression<T> {
    protected final MultipleExpression<T> left, right;

    protected BinaryOperation(MultipleExpression<T> left, MultipleExpression<T> right) {
        this.left = left;
        this.right = right;
    }

    protected String toString(String opString) {
        return "(" + left + " " + opString + " " + right + ")";
    }

    @Override
    public T evaluate(Map<String, T> vars) {
        return calculate(left.evaluate(vars), right.evaluate(vars));
    }

    protected abstract T calculate(T left, T right);
}
