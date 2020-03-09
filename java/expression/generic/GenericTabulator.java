package expression.generic;

import expression.exceptions.ExpressionCalculateException;

import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;


public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(
            String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        switch (mode) {
            case "b":
                return tabulate(new ExpressionParser<>(MyByte::parse).parse(expression),
                        MyByte::new, x1, x2, y1, y2, z1, z2);
            case "s":
                return tabulate(new ExpressionParser<>(MyShort::parse).parse(expression),
                        MyShort::new, x1, x2, y1, y2, z1, z2);
            case "i":
                return tabulate(new ExpressionParser<>(MyCheckedInt::parse).parse(expression),
                        MyCheckedInt::new, x1, x2, y1, y2, z1, z2);
            case "l":
                return tabulate(new ExpressionParser<>(MyLong::parse).parse(expression),
                        MyLong::new, x1, x2, y1, y2, z1, z2);
            case "f":
                return tabulate(new ExpressionParser<>(MyFloat::parse).parse(expression),
                        MyFloat::new, x1, x2, y1, y2, z1, z2);
            case "d":
                return tabulate(new ExpressionParser<>(MyDouble::parse).parse(expression),
                        MyDouble::new, x1, x2, y1, y2, z1, z2);
            case "bi":
                return tabulate(new ExpressionParser<>(MyBigInt::parse).parse(expression),
                        MyBigInt::new, x1, x2, y1, y2, z1, z2);
            default:
                throw new IllegalArgumentException("Unknown type identifier '" + mode + "'");
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);
        TripleExpression<MyDouble> exp =
                new ExpressionParser<>(MyDouble::parse).parse(in.nextLine());
        System.out.println(exp);
        System.out.println(exp.evaluate(
                new MyDouble(in.nextDouble()),
                new MyDouble(in.nextDouble()),
                new MyDouble(in.nextDouble())
        ).getValue());
    }
}
