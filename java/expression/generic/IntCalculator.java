package expression.generic;

public class IntCalculator extends Calculator<Integer> {
    @Override
    public Integer add(Integer left, Integer right) {
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) {
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        try {
            return left / right;
        } catch (ArithmeticException e) {
            throw new ExpressionDBZException
                    ("Division by zero: " + left + " / " + right, e);
        }
    }

    @Override
    public Integer negate(Integer arg) {
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
