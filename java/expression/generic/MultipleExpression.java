package expression.generic;

import java.util.Map;

public interface MultipleExpression <T extends MyNumber<T>> extends TripleExpression<T> {
    T evaluate(Map<String, T> vars);

    @Override
    default T evaluate(T x, T y, T z) {
        return evaluate(Map.of("x", x, "y", y, "z", z));
    }
}
