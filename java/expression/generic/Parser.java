package expression.generic;

public interface Parser <T extends MyNumber<T>> {
    MultipleExpression<T> parse(String expression);
}
