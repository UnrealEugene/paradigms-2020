package expression.generic;

public interface TripleExpression <T extends MyNumber<T>> {
    T evaluate(T x, T y, T z);
}
