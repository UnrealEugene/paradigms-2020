package expression.generic;

import java.math.BigInteger;

public class BigIntCalculator extends Calculator<BigInteger> {
    @Override
    public BigInteger add(BigInteger left, BigInteger right) {
        return left.add(right);
    }

    @Override
    public BigInteger subtract(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }

    @Override
    public BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    @Override
    public BigInteger divide(BigInteger left, BigInteger right) {
        try {
            return left.divide(right);
        } catch (ArithmeticException e) {
            throw new ExpressionDBZException
                    ("Division by zero: " + left + " / " + right, e);
        }
    }

    @Override
    public BigInteger negate(BigInteger arg) {
        return arg.negate();
    }

    @Override
    public BigInteger bitCount(BigInteger arg) {
        return new BigInteger(String.valueOf(arg.bitCount()));
    }

    @Override
    public int compareWith(BigInteger left, BigInteger right) {
        return left.compareTo(right);
    }

    @Override
    public BigInteger parse(String str) {
        return new BigInteger(str);
    }

    @Override
    public BigInteger valueOf(int arg) {
        return new BigInteger(String.valueOf(arg));
    }
}
