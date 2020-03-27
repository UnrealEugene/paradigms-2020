package expression.generic;

import java.util.Map;

public abstract class UnaryOperation <T extends Number> implements MultipleExpression<T> {
    protected final MultipleExpression<T> arg;

    protected UnaryOperation(MultipleExpression<T> arg) {
        this.arg = arg;
    }

    protected String toString(String opString) {
        return opString + "(" + arg.toString() + ")";
    }

    @Override
    public T evaluate(Calculator<T> calc, Map<String, T> vars) {
        return calculate(calc, arg.evaluate(calc, vars));
    }

    protected abstract T calculate(Calculator<T> calc, T arg);
}
