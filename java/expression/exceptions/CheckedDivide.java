package expression.exceptions;

import expression.Divide;
import expression.MultipleExpression;

public class CheckedDivide extends Divide implements MultipleExpression {
    public CheckedDivide(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int left, int right) {
        if (right == 0) {
            throw new ExpressionDBZException("Division by zero: " + left + " / " + right);
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new ExpressionOverflowException
                    ("Divide overflow: " + left + " / " + right + " > " + Integer.MAX_VALUE);
        }
        return super.calculate(left, right);
    }
}
