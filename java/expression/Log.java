package expression;

public class Log extends BinaryOperation implements MultipleExpression {
    public Log(MultipleExpression val, MultipleExpression base) {
        super(val, base);
    }

    @Override
    public String toString() {
        return super.toString("//");
    }

    @Override
    public String toMiniString() {
        return super.toMiniString("//");
    }

    @Override
    protected int calculate(int val, int base) { return discreteLog(base, val); }

    private int discreteLog(int a, int x) {
        if (a <= 1) {
            return 0;
        }
        int result = 0;
        while (x >= a) {
            x /= a;
            result++;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode("//");
    }

    @Override
    public OperationPriority getPriority() {
        return OperationPriority.HIGHER;
    }
}
