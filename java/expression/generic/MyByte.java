package expression.generic;

public class MyByte implements MyNumber<MyByte> {
    private final Byte value;

    public MyByte(Number value) {
        this.value = value.byteValue();
    }

    @Override
    public MyByte add(MyByte other) {
        return new MyByte(value + other.value);
    }

    @Override
    public MyByte subtract(MyByte other) {
        return new MyByte(value - other.value);
    }

    @Override
    public MyByte multiply(MyByte other) {
        return new MyByte(value * other.value);
    }

    @Override
    public MyByte divide(MyByte other) {
        return new MyByte(value / other.value);
    }

    @Override
    public MyByte negate() {
        return new MyByte(-value);
    }

    @Override
    public MyByte bitCount() {
        return new MyByte((byte) Integer.bitCount(value & 0xff));
    }

    @Override
    public int compareWith(MyByte other) {
        return Byte.compare(value, other.value);
    }

    public static MyByte parse(String str) {
        return new MyByte(Byte.parseByte(str));
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
