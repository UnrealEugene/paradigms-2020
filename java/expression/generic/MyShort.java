package expression.generic;

public class MyShort implements MyNumber<MyShort> {
    private final Short value;

    public MyShort(Number value) {
        this.value = value.shortValue();
    }

    @Override
    public MyShort add(MyShort other) {
        return new MyShort(value + other.value);
    }

    @Override
    public MyShort subtract(MyShort other) {
        return new MyShort(value - other.value);
    }

    @Override
    public MyShort multiply(MyShort other) {
        return new MyShort(value * other.value);
    }

    @Override
    public MyShort divide(MyShort other) {
        return new MyShort(value / other.value);
    }

    @Override
    public MyShort negate() {
        return new MyShort(-value);
    }

    public static MyShort parse(String str) {
        return new MyShort(Short.parseShort(str));
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
