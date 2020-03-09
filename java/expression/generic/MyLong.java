package expression.generic;

public class MyLong implements MyNumber<MyLong> {
    private final Long value;

    public MyLong(Number value) {
        this.value = value.longValue();
    }

    @Override
    public MyLong add(MyLong other) {
        return new MyLong(value + other.value);
    }

    @Override
    public MyLong subtract(MyLong other) {
        return new MyLong(value - other.value);
    }

    @Override
    public MyLong multiply(MyLong other) {
        return new MyLong(value * other.value);
    }

    @Override
    public MyLong divide(MyLong other) {
        return new MyLong(value / other.value);
    }

    @Override
    public MyLong negate() {
        return new MyLong(-value);
    }

    public static MyLong parse(String str) {
        return new MyLong(Long.parseLong(str));
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
