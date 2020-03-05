package expression;

import java.util.Map;
import java.util.Objects;

public class Const implements MultipleExpression {
    private final int value;

    public Const(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Const c = (Const) obj;
        return this.value == c.value;
    }

    @Override
    public int evaluate(Map<String, Integer> vars) {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
