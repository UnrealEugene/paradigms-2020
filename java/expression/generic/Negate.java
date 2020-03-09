package expression.generic;

public class Negate <T extends MyNumber<T>> extends UnaryOperation<T> {

    public Negate(MultipleExpression<T> arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("*");
    }

    @Override
    protected T calculate(T arg) { return arg.negate(); }
}
