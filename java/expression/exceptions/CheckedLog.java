package expression.exceptions;

import expression.Log;
import expression.MultipleExpression;

public class CheckedLog extends Log implements MultipleExpression {
    public CheckedLog(MultipleExpression val, MultipleExpression base) {
        super(val, base);
    }

    @Override
    protected int calculate(int val, int base) {
        if (base <= 1) {
            throw new ExpressionLogException("Invalid logarithm base: " + val + " // " + base);
        }
        if (val <= 0) {
            throw new ExpressionLogException("Logarithm of non-positive: " + val + " // " + base);
        }
        return super.calculate(val, base);
    }
}
