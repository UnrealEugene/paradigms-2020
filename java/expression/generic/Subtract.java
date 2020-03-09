package expression.generic;

public class Subtract <T extends MyNumber<T>> extends BinaryOperation<T> {

    public Subtract(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("-");
    }

    @Override
    protected T calculate(T left, T right) { return left.subtract(right); }
}
