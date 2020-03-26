package expression.generic;

import expression.exceptions.ExpressionCalculateException;
import expression.exceptions.ExpressionDBZException;

import java.math.BigInteger;

public class MyBigInt extends MyNumber<MyBigInt, BigInteger> {
    public MyBigInt(BigInteger value) {
        super(value);
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
        try {
            return new MyBigInt(value.divide(other.value));
        } catch (ArithmeticException e) {
            throw new ExpressionDBZException
                    ("Division by zero: " + value + " / " + other.value, e);
        }
    }

    @Override
    public MyBigInt negate() {
        return new MyBigInt(value.negate());
    }

    @Override
    public MyBigInt bitCount() {
        return new MyBigInt(new BigInteger(String.valueOf(value.bitCount())));
    }

    @Override
    public int compareWith(MyBigInt other) {
        return value.compareTo(other.value);
    }

    public static MyBigInt parse(String str) {
        return new MyBigInt(new BigInteger(str));
    }
}
