package expression.exceptions;

import expression.MultipleExpression;
import expression.Negate;

public class CheckedNegate extends Negate implements MultipleExpression {
    public CheckedNegate(MultipleExpression arg) {
        super(arg);
    }

    @Override
    protected int calculate(int arg) {
        if (arg == Integer.MIN_VALUE) {
            throw new ExpressionOverflowException
                    ("Negate overflow: -" + arg + " > " + Integer.MAX_VALUE);
        }
        return super.calculate(arg);
    }
}
