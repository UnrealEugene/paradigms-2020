package expression;

public class Negate extends UnaryOperation {
    public Negate(MultipleExpression arg) {
        super(arg);
    }

    @Override
    public String toString() {
        return super.toString("-");
    }

    @Override
    public String toMiniString() {
        return super.toMiniString("-");
    }

    @Override
    protected int calculate(int arg) { return -arg; }

    @Override
    public int hashCode() {
        return super.hashCode("-");
    }
}
