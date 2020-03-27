package expression.generic;

import java.util.Map;

public class Const <T extends Number> implements MultipleExpression<T> {
    private final T value;

    public Const(final T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public T evaluate(Calculator<T> calc, Map<String, T> vars) {
        return value;
    }
}
