package expression;

public class Pow extends BinaryOperation implements MultipleExpression {
    public Pow(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return super.toString("**");
    }

    @Override
    public String toMiniString() {
        return super.toMiniString("**");
    }

    @Override
    protected int calculate(int left, int right) { return fastPow(left, right); }

    private int fastPow(int a, int p) {
        int result = 1;
        while (p > 0) {
            if (p % 2 == 1) {
                result *= a;
            }
            a *= a;
            p >>= 1;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode("**");
    }

    @Override
    public OperationPriority getPriority() {
        return OperationPriority.HIGHER;
    }
}
