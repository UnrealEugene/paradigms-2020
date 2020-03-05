package expression.exceptions;

import expression.Log2;
import expression.MultipleExpression;

public class CheckedLog2 extends Log2 implements MultipleExpression {
    public CheckedLog2(MultipleExpression arg) {
        super(arg);
    }

    @Override
    protected int calculate(int arg) {
        if (arg <= 0) {
            throw new ExpressionLogException("Logarithm of non-positive: log2 " + arg);
        }
        return super.calculate(arg);
    }
}
