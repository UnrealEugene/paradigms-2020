package expression.generic;

import java.util.Map;

public interface MultipleExpression <T extends MyNumber<T, ? extends Number>>  {
    T evaluate(Map<String, T> vars);
}
