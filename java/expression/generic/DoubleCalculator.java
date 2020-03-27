package expression.generic;

public class DoubleCalculator extends Calculator<Double> {
    @Override
    public Double add(Double left, Double right) {
        return left + right;
    }

    @Override
    public Double subtract(Double left, Double right) {
        return left - right;
    }

    @Override
    public Double multiply(Double left, Double right) {
        return left * right;
    }

    @Override
    public Double divide(Double left, Double right) {
        return left / right;
    }

    @Override
    public Double negate(Double arg) {
        return -arg;
    }

    @Override
    public Double bitCount(Double arg) {
        return (double) Long.bitCount(Double.doubleToLongBits(arg));
    }

    @Override
    public int compareWith(Double left, Double right) {
        return Double.compare(left, right);
    }

    @Override
    public Double parse(String str) {
        return Double.parseDouble(str);
    }

    @Override
    public Double valueOf(int arg) {
        return (double) arg;
    }
}
