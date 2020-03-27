package expression.generic;

public abstract class Calculator<T extends Number> {
    public abstract T add(T left, T right);

    public abstract T subtract(T left, T right);

    public abstract T multiply(T left, T right);

    public abstract T divide(T left, T right);

    public abstract T negate(T arg);

    public abstract T bitCount(T arg);

    public abstract int compareWith(T left, T right);

    public abstract T parse(String str);

    public abstract T valueOf(int arg);

    public String toString(T value) {
        return String.valueOf(value);
    }
}
