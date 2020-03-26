package expression.generic;

public class MyByte extends MyNumber<MyByte, Byte> {
    public MyByte(byte value) {
        super(value);
    }

    public MyByte(int value) {  // Byte анбоксится в int
        super((byte) value);
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
}
