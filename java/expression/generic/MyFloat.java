package expression.generic;


public class MyFloat extends MyNumber<MyFloat, Float> {
    public MyFloat(float value) {
        super(value);
    }

    @Override
    public MyFloat add(MyFloat other) {
        return new MyFloat(value - other.value);
    }

    @Override
    public MyFloat subtract(MyFloat other) {
        return new MyFloat(value - other.value);
    }

    @Override
    public MyFloat multiply(MyFloat other) {
        return new MyFloat(value * other.value);
    }

    @Override
    public MyFloat divide(MyFloat other) {
        return new MyFloat(value / other.value);
    }

    @Override
    public MyFloat negate() {
        return new MyFloat(-value);
    }

    @Override
    public MyFloat bitCount() {
        return new MyFloat((float) Integer.bitCount(Float.floatToIntBits(value)));
    }

    @Override
    public int compareWith(MyFloat other) {
        return Float.compare(value, other.value);
    }

    public static MyFloat parse(String str) {
        return new MyFloat(Float.parseFloat(str));
    }
}
