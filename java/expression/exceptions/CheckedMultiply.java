package expression.exceptions;

import expression.MultipleExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply implements MultipleExpression {
    public CheckedMultiply(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int left, int right) {
        switch (canMultiply(left, right)) {
            case -1:
                throw new ExpressionOverflowException
                        ("Multiply overflow: " + left + " * " + right + " < " + Integer.MIN_VALUE);
            case 1:
                throw new ExpressionOverflowException
                        ("Multiply overflow: " + left + " * " + right + " > " + Integer.MAX_VALUE);
        }
        return super.calculate(left, right);
    }

    static int canMultiply(int left, int right) {
        if (left > 0) {
            if (right > 0) {
                if (left > Integer.MAX_VALUE / right) {
                    return 1;
                }
            } else {
                if (right < Integer.MIN_VALUE / left) {
                    return -1;
                }
            }
        } else {
            if (right > 0) {
                if (left < Integer.MIN_VALUE / right) {
                    return -1;
                }
            } else {
                if (left != 0 && right < Integer.MAX_VALUE / left) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
