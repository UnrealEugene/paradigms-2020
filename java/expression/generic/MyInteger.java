package expression.generic;

public class MyInteger extends MyNumber<MyInteger, Integer> {
    public MyInteger(int value) {
        super(value);
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

    @Override
    public MyInteger bitCount() {
        return new MyInteger(Integer.bitCount(value));
    }

    @Override
    public int compareWith(MyInteger other) {
        return Integer.compare(value, other.value);
    }

    public static MyInteger parse(String str) {
        return new MyInteger(Integer.parseInt(str));
    }
}
