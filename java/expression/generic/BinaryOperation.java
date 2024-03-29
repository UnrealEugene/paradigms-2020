package expression.generic;

import java.util.Map;

public abstract class BinaryOperation <T extends Number> implements MultipleExpression<T> {
    protected final MultipleExpression<T> left, right;

    protected BinaryOperation(MultipleExpression<T> left, MultipleExpression<T> right) {
        this.left = left;
        this.right = right;
    }

    protected String toString(String opString) {
        return "(" + left + " " + opString + " " + right + ")";
    }

    @Override
    public T evaluate(Calculator<T> calc, Map<String, T> vars) {
        return calculate(calc, left.evaluate(calc, vars), right.evaluate(calc, vars));
    }

    protected abstract T calculate(Calculator<T> calc, T left, T right);
}
