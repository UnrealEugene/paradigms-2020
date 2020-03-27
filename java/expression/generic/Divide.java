package expression.generic;

public class Divide <T extends Number> extends BinaryOperation<T> {

    public Divide(MultipleExpression<T> left, MultipleExpression<T> right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("/");
    }

    @Override
    protected T calculate(Calculator<T> calc, T left, T right) { return calc.divide(left, right); }
}
