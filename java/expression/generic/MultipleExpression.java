package expression.generic;

import java.util.Map;

public interface MultipleExpression <T extends Number>  {
    T evaluate(Calculator<T> calc, Map<String, T> vars);
}
