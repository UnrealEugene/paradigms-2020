package expression.exceptions;

import expression.Add;
import expression.MultipleExpression;

public class CheckedAdd extends Add implements MultipleExpression {
    CheckedAdd(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int left, int right) {
        if (left > right) {
            int t = right;
            right = left;
            left = t;
        }
        if (left > 0 || right < 0) {
            if (left >= 0 && right > Integer.MAX_VALUE - left) {
                throw new ExpressionOverflowException
                        ("Addition overflow: " + left + " + " + right + " > " + Integer.MAX_VALUE);
            }
            if (right <= 0 && left < Integer.MIN_VALUE - right) {
                throw new ExpressionOverflowException
                        ("Addition overflow: " + left + " + " + right + " < " + Integer.MIN_VALUE);
            }
        }
        return super.calculate(left, right);
    }
}
