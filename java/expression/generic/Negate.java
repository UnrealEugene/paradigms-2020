package expression.generic;

public class Negate <T extends Number> extends UnaryOperation<T> {

    public Negate(MultipleExpression<T> arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("-");
    }

    @Override
    protected T calculate(Calculator<T> calc, T arg) { return calc.negate(arg); }
}
