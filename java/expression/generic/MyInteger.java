package expression.generic;

import expression.exceptions.ExpressionCalculateException;
import expression.exceptions.ExpressionDBZException;
import expression.exceptions.ExpressionOverflowException;

public class MyInteger implements MyNumber<MyInteger> {
    private final int value;

    public MyInteger(Number value) {
        this.value = value.intValue();
    }

    @Override
    public MyInteger add(MyInteger other) {
        int left = value, right = other.value;
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
        return new MyInteger(left + right);
    }

    @Override
    public MyInteger subtract(MyInteger other) {
        int left = value, right = other.value;
        if (left >= 0 && right <= 0 && left > Integer.MAX_VALUE + right) {
            throw new ExpressionCalculateException
                    ("Subtract overflow: " + left + " - " + right + " > " + Integer.MAX_VALUE);
        }
        if (left <= 0 && right >= 0 && left < Integer.MIN_VALUE + right) {
            throw new ExpressionCalculateException
                    ("Subtract overflow: " + left + " - " + right + " < " + Integer.MIN_VALUE);
        }
        return new MyInteger(left - right);
    }

    @Override
    public MyInteger multiply(MyInteger other) {
        int left = value, right = other.value;
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
        return new MyInteger(left * right);
    }

    @Override
    public MyInteger divide(MyInteger other) {
        int left = value, right = other.value;
        if (right == 0) {
            throw new ExpressionDBZException("Division by zero: " + left + " / " + right);
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new ExpressionOverflowException
                    ("Divide overflow: " + left + " / " + right + " > " + Integer.MAX_VALUE);
        }
        return new MyInteger(left / right);
    }

    @Override
    public MyInteger negate() {
        return new MyInteger(-value);
    }

    public static MyInteger parse(String str) {
        return new MyInteger(Integer.parseInt(str));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Number getValue() {
        return value;
    }
}
