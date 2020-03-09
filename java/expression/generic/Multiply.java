package expression.generic;

public class Multiply <T extends MyNumber<T>> extends BinaryOperation<T> {

    public Multiply(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("*");
    }

    @Override
    protected T calculate(T left, T right) { return left.multiply(right); }
}
