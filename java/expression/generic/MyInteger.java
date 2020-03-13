package expression.generic;

public class MyInteger implements MyNumber<MyInteger> {
    private final Integer value;

    public MyInteger(Number value) {
        this.value = value.intValue();
    }

    @Override
    public MyInteger add(MyInteger other) {
        return new MyInteger(value + other.value);
    }

    @Override
    public MyInteger subtract(MyInteger other) {
        return new MyInteger(value - other.value);
    }

    @Override
    public MyInteger multiply(MyInteger other) {
        return new MyInteger(value * other.value);
    }

    @Override
    public MyInteger divide(MyInteger other) {
        return new MyInteger(value / other.value);
    }

    @Override
    public MyInteger negate() {
        return new MyInteger(-value);
    }

    public static MyInteger parse(String str) {
        return new MyInteger(Integer.parseInt(str));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Number getValue() {
        return value;
    }
}
