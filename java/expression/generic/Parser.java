package expression.generic;

public interface Parser <T extends MyNumber<T>> {
    TripleExpression<T> parse(String expression);
}
