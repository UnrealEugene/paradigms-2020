package expression.generic;

import java.math.BigInteger;

public class MyBigInt implements MyNumber<MyBigInt> {
    private final BigInteger value;

    public MyBigInt(Number value) {
        this.value = new BigInteger(String.valueOf(value));
    }

    @Override
    public MyBigInt add(MyBigInt other) {
        return new MyBigInt(value.add(other.value));
    }

    @Override
    public MyBigInt subtract(MyBigInt other) {
        return new MyBigInt(value.subtract(other.value));
    }

    @Override
    public MyBigInt multiply(MyBigInt other) {
        return new MyBigInt(value.multiply(other.value));
    }

    @Override
    public MyBigInt divide(MyBigInt other) {
        return new MyBigInt(value.divide(other.value));
    }

    @Override
    public MyBigInt negate() {
        return new MyBigInt(value.negate());
    }

    public static MyBigInt parse(String str) {
        return new MyBigInt(new BigInteger(str));
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
