package expression;

public class Pow2 extends UnaryOperation implements MultipleExpression {
    public Pow2(MultipleExpression arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("pow2");
    }

    @Override
    public String toMiniString() {
        return super.toMiniString("pow2");
    }

    @Override
    protected int calculate(int arg) { return 1 << arg; }

    @Override
    public int hashCode() {
        return super.hashCode("pow2");
    }
}
