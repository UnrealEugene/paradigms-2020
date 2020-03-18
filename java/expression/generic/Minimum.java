package expression.generic;

public class Minimum <T extends MyNumber<T>> extends BinaryOperation<T> {

    public Minimum(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("min");
    }

    @Override
    protected T calculate(T left, T right) { return left.compareWith(right) <= 0 ? left : right; }
}
