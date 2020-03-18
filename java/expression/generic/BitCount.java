package expression.generic;

public class BitCount <T extends MyNumber<T>> extends UnaryOperation<T> {

    public BitCount(MultipleExpression<T> arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("count");
    }

    @Override
    protected T calculate(T arg) { return arg.bitCount(); }
}
