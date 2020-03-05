package expression;

public class Log2 extends UnaryOperation implements MultipleExpression {
    public Log2(MultipleExpression arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("log2");
    }

    @Override
    public String toMiniString() {
        return super.toMiniString("log2");
    }

    @Override
    protected int calculate(int arg) { return discreteLog2(arg); }

    private int discreteLog2(int x) {
        int result = 0;
        for (int i = 16; i > 0; i >>= 1) {
            if ((x >> i) > 0) {
                result += i;
                x >>= i;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode("log2");
    }
}
