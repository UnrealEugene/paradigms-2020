package expression.generic;

public class BitCount <T extends Number> extends UnaryOperation<T> {

    public BitCount(MultipleExpression<T> arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("count");
    }

    @Override
    protected T calculate(Calculator<T> calc, T arg) { return calc.bitCount(arg); }
}
