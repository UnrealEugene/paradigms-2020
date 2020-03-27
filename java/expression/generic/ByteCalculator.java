package expression.generic;

public class ByteCalculator extends Calculator<Byte> {
    @Override
    public Byte add(Byte left, Byte right) {
        return (byte) (left + right);
    }

    @Override
    public Byte subtract(Byte left, Byte right) {
        return (byte) (left - right);
    }

    @Override
    public Byte multiply(Byte left, Byte right) {
        return (byte) (left * right);
    }

    @Override
    public Byte divide(Byte left, Byte right) {
        try {
            return (byte) (left / right);
        } catch (ArithmeticException e) {
            throw new ExpressionDBZException
                    ("Division by zero: " + left + " / " + right, e);
        }
    }

    @Override
    public Byte negate(Byte arg) {
        return (byte) (-arg);
    }

    @Override
    public Byte bitCount(Byte arg) {
        return (byte) Integer.bitCount(arg & 0xff);
    }

    @Override
    public int compareWith(Byte left, Byte right) {
        return Byte.compare(left, right);
    }

    @Override
    public Byte parse(String str) {
        return Byte.parseByte(str);
    }

    @Override
    public Byte valueOf(int arg) {
        return (byte) arg;
    }
}
