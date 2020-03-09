package expression.generic;

import expression.exceptions.ExpressionCalculateException;

import java.util.function.Function;


public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(
            String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        switch (mode) {
            case "i":
                return tabulate(new ExpressionParser<>(MyInteger::parse).parse(expression),
                        MyInteger::new, x1, x2, y1, y2, z1, z2);
            case "d":
                return tabulate(new ExpressionParser<>(MyDouble::parse).parse(expression),
                        MyDouble::new, x1, x2, y1, y2, z1, z2);
            case "bi":
                return tabulate(new ExpressionParser<>(MyBigInt::parse).parse(expression),
                        MyBigInt::new, x1, x2, y1, y2, z1, z2);
            default:
                throw new IllegalArgumentException("Unknown type '" + mode + "'");
        }
    }

    private <T extends MyNumber<T>> Object[][][] tabulate(
            TripleExpression<T> expression, Function<Number, T> createFunc,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        result[x - x1][y - y1][z - z1] = expression.evaluate(
                                createFunc.apply(x),
                                createFunc.apply(y),
                                createFunc.apply(z)
                        ).getValue();
                    } catch (ExpressionCalculateException | ArithmeticException e) {
                        result[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}
