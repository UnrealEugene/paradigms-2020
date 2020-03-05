package expression.exceptions;

import expression.MultipleExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract implements MultipleExpression {
    CheckedSubtract(MultipleExpression left, MultipleExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int left, int right) {
        if (left >= 0 && right <= 0 && left > Integer.MAX_VALUE + right) {
                throw new ExpressionCalculateException
                        ("Subtract overflow: " + left + " - " + right + " > " + Integer.MAX_VALUE);
        }
        if (left <= 0 && right >= 0 && left < Integer.MIN_VALUE + right) {
            throw new ExpressionCalculateException
                    ("Subtract overflow: " + left + " - " + right + " < " + Integer.MIN_VALUE);
        }
        return super.calculate(left, right);
    }
}
