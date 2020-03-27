package expression.generic;

import java.util.Map;

public class Variable <T extends Number> implements MultipleExpression<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public T evaluate(Calculator<T> calc, Map<String, T> vars) {
        return vars.get(name);
    }
}
