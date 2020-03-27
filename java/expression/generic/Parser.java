package expression.generic;

public interface Parser <T extends Number> {
    MultipleExpression<T> parse(String expression);
}
