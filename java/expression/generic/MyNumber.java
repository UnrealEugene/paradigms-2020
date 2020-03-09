package expression.generic;

public interface MyNumber<T> {
    T add(T other);
    T subtract(T other);
    T multiply(T other);
    T divide(T other);
    T negate();
    Number getValue();
}
