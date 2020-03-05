package expression.exceptions;

import expression.MultipleExpression;
import expression.Pow2;

public class CheckedPow2 extends Pow2 implements MultipleExpression {
    public CheckedPow2(MultipleExpression arg) {
        super(arg);
    }

    @Override
    protected int calculate(int arg) {
        if (arg > 31) {
            throw new ExpressionOverflowException
                    ("Power overflow: pow2 " + arg + " > " + Integer.MAX_VALUE);
        }
        if (arg < 0) {
            throw new ExpressionPowException("Power of negative: pow2 " + arg);
        }
        return super.calculate(arg);
    }
}
