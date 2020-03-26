package expression.generic;

import expression.exceptions.ExpressionCalculateException;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

interface MyNumberConstructor <T extends MyNumber<T, S>, S extends Number> {
    //    <T extends MyNumber<T, S>, S extends Number> T costruct(S value);
    T costruct(S value);
}

interface MyNumberParser <T extends MyNumber<T, ? extends Number>> {
    T parse(String str);
}

public class GenericTabulator implements Tabulator {
    public static class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

//    Map<String, MyNumberParser<? extends MyNumber<?, ?>>> parsers = Map.of(
//        "b", MyByte::parse
//    );

//    Map<String, Pair<MyNumberParser<? extends MyNumber<?, ?>>, MyNumberConstructor<?, ?>>> functions = Map.of(
//            "b", new Pair<MyNumberParser<MyByte>, MyNumberConstructor<MyByte, Byte>>
//                    (MyByte::parse, MyByte::new),
//            "u", new Pair<MyNumberParser<MyInteger>, MyNumberConstructor<MyInteger, Integer>>
//                    (MyInteger::parse, MyInteger::new),
//            "i", new Pair<MyNumberParser<MyCheckedInt>, MyNumberConstructor<MyCheckedInt, Integer>>
//                    (MyCheckedInt::parse, MyCheckedInt::new),
//            "f", new Pair<MyNumberParser<MyFloat>, MyNumberConstructor<MyFloat, Float>>
//                    (MyFloat::parse, MyFloat::new),
//            "d", new Pair<MyNumberParser<MyDouble>, MyNumberConstructor<MyDouble, Double>>
//                    (MyDouble::parse, MyDouble::new),
//            "bi", new Pair<MyNumberParser<MyBigInt>, MyNumberConstructor<MyBigInt, BigInteger>>
//                    (MyBigInt::parse, MyBigInt::new)
//    );


    Map<String, MyNumberParser<MyNumber>> mp =
            Map.of("b", MyByte::parse);

    @Override
    public Object[][][] tabulate(
            String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        return tabulate(new ExpressionParser<>(MyByte::parse).parse(expression),
                MyByte::new, x1, x2, y1, y2, z1, z2);
    }
    private <T extends MyNumber<T, S>, S extends Number> Object[][][] tabulate(
            MultipleExpression<T> expression, Function<Number, T> createFunc,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int x = x1; x <= x2; x++) {
            T varX = createFunc.apply(x);
            for (int y = y1; y <= y2; y++) {
                T varY = createFunc.apply(y);
                for (int z = z1; z <= z2; z++) {
                    T varZ = createFunc.apply(z);
                    try {
                        result[x - x1][y - y1][z - z1] = expression.evaluate(Map.of(
                                "x", varX,
                                "y", varY,
                                "z", varZ
                        )).getValue();
                    } catch (ExpressionCalculateException ignored) { }
                }
            }
        }
        return result;
    }
}
