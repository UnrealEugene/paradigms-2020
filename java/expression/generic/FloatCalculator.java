package expression.generic;


public class FloatCalculator extends Calculator<Float> {
    @Override
    public Float add(Float left, Float right) {
        return left + right;
    }

    @Override
    public Float subtract(Float left, Float right) {
        return left - right;
    }

    @Override
    public Float multiply(Float left, Float right) {
        return left * right;
    }

    @Override
    public Float divide(Float left, Float right) {
        return left / right;
    }

    @Override
    public Float negate(Float arg) {
        return -arg;
    }

    @Override
    public Float bitCount(Float arg) {
        return (float) Integer.bitCount(Float.floatToIntBits(arg));
    }

    @Override
    public int compareWith(Float left, Float right) {
        return Float.compare(left, right);
    }

    @Override
    public Float parse(String str) {
        return Float.parseFloat(str);
    }

    @Override
    public Float valueOf(int arg) {
        return (float) arg;
    }
}
