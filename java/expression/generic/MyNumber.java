package expression.generic;

public abstract class MyNumber<T extends MyNumber<T, S>, S extends Number> {
    protected final S value;

    protected MyNumber(S value) {
        this.value = value;
    }

    public abstract T add(T other);

    public abstract T subtract(T other);

    public abstract T multiply(T other);

    public abstract T divide(T other);

    public abstract T negate();

    public abstract T bitCount();

    public abstract int compareWith(T other);

    public String toString() {
        return String.valueOf(value);
    }

    public S getValue() {
        return value;
    }
}
