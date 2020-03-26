package expression.generic;

public class Add <T extends MyNumber<T, ? extends Number>> extends BinaryOperation<T> {

    public Add(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("+");
    }

    @Override
    protected T calculate(T left, T right) { return left.add(right); }
}
