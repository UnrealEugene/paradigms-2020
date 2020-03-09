package expression.generic;

public class MyDouble implements MyNumber<MyDouble> {
    private final double value;

    public MyDouble(Number value) {
        this.value = value.doubleValue();
    }

    @Override
    public MyDouble add(MyDouble other) {
        return new MyDouble(value + other.value);
    }

    @Override
    public MyDouble subtract(MyDouble other) {
        return new MyDouble(value - other.value);
    }

    @Override
    public MyDouble multiply(MyDouble other) {
        return new MyDouble(value * other.value);
    }

    @Override
    public MyDouble divide(MyDouble other) {
        return new MyDouble(value / other.value);
    }

    @Override
    public MyDouble negate() {
        return new MyDouble(-value);
    }

    public static MyDouble parse(String str) {
        return new MyDouble(Double.parseDouble(str));
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
