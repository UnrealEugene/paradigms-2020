package expression.generic;

import java.util.Map;

interface MyNumberParser <T extends Number> {
    T parse(String str);
}

public class GenericTabulator implements Tabulator {
    Map<String, Calculator<? extends Number>> calculators = Map.of(
            "b", new ByteCalculator(),
            "u", new IntCalculator(),
            "i", new CheckedIntCalculator(),
            "f", new FloatCalculator(),
            "d", new DoubleCalculator(),
            "bi", new BigIntCalculator()
    );

    @Override
    public Object[][][] tabulate(
            String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        return tabulate(calculators.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T extends Number> Object[][][] tabulate(
            Calculator<T> calc, String str, int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        MultipleExpression<T> exp = new ExpressionParser<>(calc::parse).parse(str);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int x = x1; x <= x2; x++) {
            T valX = calc.valueOf(x);
            for (int y = y1; y <= y2; y++) {
                T valY = calc.valueOf(y);
                for (int z = z1; z <= z2; z++) {
                    T valZ = calc.valueOf(z);
                    try {
                        result[x - x1][y - y1][z - z1] =
                                exp.evaluate(calc, Map.of("x", valX, "y", valY, "z", valZ));
                    } catch (ExpressionCalculateException ignored) { }
                }
            }
        }
        return result;
    }
}
