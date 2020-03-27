package expression.generic;

public class Multiply <T extends Number> extends BinaryOperation<T> {

    public Multiply(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("*");
    }

    @Override
    protected T calculate(Calculator<T> calc, T left, T right) { return calc.multiply(left, right); }
}
