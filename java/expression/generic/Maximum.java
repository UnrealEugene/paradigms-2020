package expression.generic;

public class Maximum <T extends MyNumber<T>> extends BinaryOperation<T> {

    public Maximum(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("max");
    }

    @Override
    protected T calculate(T left, T right) { return left.compareWith(right) >= 0 ? left : right; }
}
