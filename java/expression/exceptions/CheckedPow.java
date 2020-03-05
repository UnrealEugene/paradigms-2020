package expression.exceptions;

import expression.MultipleExpression;
import expression.Pow;

public class CheckedPow extends Pow implements MultipleExpression {
    public CheckedPow(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int left, int right) {
        if (right < 0 || (left == 0 && right == 0)) {
            throw new ExpressionPowException("Power undefined behaviour: " + left + " ** " + right);
        }
        if (left == -1) {
            return right % 2 == 0 ? 1 : -1;
        }
        if (left == 0 || left == 1) {
            return left;
        }

        int result = 1;
        for (int i = 0; i < right; i++) {
            if (CheckedMultiply.canMultiply(result, left) != 0) {
                throw new ExpressionOverflowException("Power overflow: " + left + " ** " + right);
            }
            result *= left;
        }
        return result;
    }
}
