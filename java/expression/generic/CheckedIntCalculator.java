package expression.generic;

public class CheckedIntCalculator extends Calculator<Integer> {
    @Override
    public Integer add(Integer left, Integer right) {
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
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) {
        if (left >= 0 && right <= 0 && left > Integer.MAX_VALUE + right) {
            throw new ExpressionCalculateException
                    ("Subtract overflow: " + left + " - " + right + " > " + Integer.MAX_VALUE);
        }
        if (left <= 0 && right >= 0 && left < Integer.MIN_VALUE + right) {
            throw new ExpressionCalculateException
                    ("Subtract overflow: " + left + " - " + right + " < " + Integer.MIN_VALUE);
        }
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        if (left > 0) {
            if (right > 0) {
                if (left > Integer.MAX_VALUE / right) {
                    throw new ExpressionOverflowException
                            ("Multiply overflow: " + left + " * " + right + " > " + Integer.MAX_VALUE);
                }
            } else {
                if (right < Integer.MIN_VALUE / left) {
                    throw new ExpressionOverflowException
                            ("Multiply overflow: " + left + " * " + right + " < " + Integer.MIN_VALUE);
                }
            }
        } else {
            if (right > 0) {
                if (left < Integer.MIN_VALUE / right) {
                    throw new ExpressionOverflowException
                            ("Multiply overflow: " + left + " * " + right + " < " + Integer.MIN_VALUE);
                }
            } else {
                if (left != 0 && right < Integer.MAX_VALUE / left) {
                    throw new ExpressionOverflowException
                            ("Multiply overflow: " + left + " * " + right + " > " + Integer.MAX_VALUE);
                }
            }
        }
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        if (right == 0) {
            throw new ExpressionDBZException("Division by zero: " + left + " / " + right);
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new ExpressionOverflowException
                    ("Divide overflow: " + left + " / " + right + " > " + Integer.MAX_VALUE);
        }
        return left / right;
    }



    @Override
    public Integer negate(Integer arg) {
        if (arg == Integer.MIN_VALUE) {
            throw new ExpressionOverflowException
                    ("Negate overflow: -(" + arg + ") > " + Integer.MAX_VALUE);
        }
        return -arg;
    }

    @Override
    public Integer bitCount(Integer arg) {
        return Integer.bitCount(arg);
    }

    @Override
    public int compareWith(Integer left, Integer right) {
        return Integer.compare(left, right);
    }

    @Override
    public Integer parse(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public Integer valueOf(int arg) {
        return arg;
    }
}
