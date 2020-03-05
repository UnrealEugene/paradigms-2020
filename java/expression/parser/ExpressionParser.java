package expression.parser;

import expression.*;

import java.util.List;
import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    protected static int MIN_PRIORITY = 0;
    protected static int UNARY_PRIORITY = MIN_PRIORITY + 1337;
    protected static Map<String, Integer> levels = Map.of(
            "**", MIN_PRIORITY + 3,
            "//", MIN_PRIORITY + 3,
            "*", MIN_PRIORITY + 2,
            "/", MIN_PRIORITY + 2,
            "+", MIN_PRIORITY + 1,
            "-", MIN_PRIORITY + 1
    );
    protected static List<String> opList = List.of("**", "//", "*", "/", "+", "-");


    public CommonExpression parse(String expression) {
        currentSource = new StringSource(expression);
        return parseExpression(MIN_PRIORITY);
    }

    protected MultipleExpression parseExpression(int currentPriority) {
        MultipleExpression currentExpression = null;

        /// trying to resolve first operand
        if (checkAndMove('(')) {
            currentExpression = parseExpression(MIN_PRIORITY);
            expect(')');
        } else if (checkBetween('x', 'z')) {
            currentSource.moveNext();
            currentExpression = new Variable(Character.toString(currentSource.prev()));
        } else if (checkBetween('0', '9')) {
            currentExpression = new Const(parseConstant(false));
        } else if (checkAndMove('l')) {
            expect("og2");
            currentExpression = new Log2(parseExpression(UNARY_PRIORITY));
        } else if (checkAndMove('p')) {
            expect("ow2");
            currentExpression = new Pow2(parseExpression(UNARY_PRIORITY));
        } else if (checkAndMove('-')) {
            if (checkBetween('0', '9')) {
                currentExpression = new Const(parseConstant(true));
            } else {
                currentExpression = new Negate(parseExpression(UNARY_PRIORITY));
            }
        }

        /// trying to continue expression by binary operations
        return continueExpression(currentPriority, currentExpression);
    }

    protected MultipleExpression continueExpression(int currentPriority, MultipleExpression currentExpression) {
//        while (!currentSource.isEnd()) {
//            int priority = currentPriority;
//            for (String op : opList) {
//                if (check(op)) {
//                    int opPriority = levels.get(op);
//                    if (opPriority > currentPriority) {
//                        currentSource.move(op.length());
//                        currentExpression = proceed(op, currentExpression, parseExpression(opPriority));
//                    }
//                    priority = opPriority;
//                    break;
//                }
//            }
//            if (priority <= currentPriority) {
//                break;
//            }
//        }
        return currentExpression;
    }

    protected MultipleExpression proceed(String op, MultipleExpression left, MultipleExpression right) {
        switch (op) {
            case "**":
                return new Pow(left, right);
            case "//":
                return new Log(left, right);
            case "*":
                return new Multiply(left, right);
            case "/":
                return new Divide(left, right);
            case "+":
                return new Add(left, right);
            case "-":
                return new Subtract(left, right);
        }
        return null;
    }

    protected int parseConstant(boolean sign) {
        int result = 0;
        while (checkBetween('0', '9')) {
            result *= 10;
            result += Character.getNumericValue(currentSource.current()) * (sign ? -1 : 1);
            currentSource.moveNext();
        }
        return result;
    }
}
