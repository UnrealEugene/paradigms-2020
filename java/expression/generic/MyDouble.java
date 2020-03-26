package expression.generic;

public class MyDouble extends MyNumber<MyDouble, Double> {
    public MyDouble(double value) {
        super(value);
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

    @Override
    public MyDouble bitCount() {
        return new MyDouble(Long.bitCount(Double.doubleToLongBits(value)));
    }

    @Override
    public int compareWith(MyDouble other) {
        return Double.compare(value, other.value);
    }

    public static MyDouble parse(String str) {
        return new MyDouble(Double.parseDouble(str));
    }
}
