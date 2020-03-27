package expression.generic;

public class Minimum <T extends Number> extends BinaryOperation<T> {

    public Minimum(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("min");
    }

    @Override
    protected T calculate(Calculator<T> calc, T left, T right) { return calc.compareWith(left, right) <= 0 ? left : right; }
}
