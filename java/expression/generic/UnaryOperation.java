package expression.generic;

import java.util.Map;

public abstract class UnaryOperation <T extends MyNumber<T>> implements MultipleExpression<T> {
    protected final MultipleExpression<T> arg;

    protected UnaryOperation(MultipleExpression<T> arg) {
        this.arg = arg;
    }

    protected String toString(String opString) {
        return opString + "(" + arg.toString() + ")";
    }

    @Override
    public T evaluate(Map<String, T> vars) {
        return calculate(arg.evaluate(vars));
    }

    protected abstract T calculate(T arg);
}
