package expression.generic;

public class Maximum <T extends Number> extends BinaryOperation<T> {

    public Maximum(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("max");
    }

    @Override
    protected T calculate(Calculator<T> calc, T left, T right) { return calc.compareWith(left, right) >= 0 ? left : right; }
}
