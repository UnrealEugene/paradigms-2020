package expression;

import java.util.Map;
import java.util.Objects;

public class Variable implements MultipleExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int evaluate(Map<String, Integer> vars) {
        return vars.get(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Variable variable = (Variable) obj;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
