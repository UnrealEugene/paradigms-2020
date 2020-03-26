package expression.generic;

import expression.exceptions.*;
import expression.parser.StringSource;

import java.util.List;
//
//interface MyNumberParser <T extends MyNumber<T, ? extends Number>> {
//    T parse(String str);
//}

public class ExpressionParser <T extends MyNumber<T, ? extends Number>> extends BaseParser<T> {
    private static final int UNARY_PRIORITY = 0;
    private static final List<List<String>> LEVELS = List.of(
            List.of("*", "/"),
            List.of("+", "-"),
            List.of("min", "max")
    );
    private static final int MIN_PRIORITY = UNARY_PRIORITY - LEVELS.size();
    private static final int CONST_PRIORITY = UNARY_PRIORITY + 1;
    private final MyNumberParser<T> numberParser;

    public ExpressionParser(MyNumberParser<T> numberParser) {
        this.numberParser = numberParser;
    }

    @Override
    public MultipleExpression<T> parse(String expression) {
        currentSource = new StringSource(expression);
        MultipleExpression<T> result = parseExpression(MIN_PRIORITY);
        if (!currentSource.isEnd()) {
            throw new ParserUnresolvedSymbolException
                    ("Unresolved symbol '" + currentSource.current() + "'", currentSource.getCurrentPos());
        }
        return result;
    }

    private MultipleExpression<T> parseExpression(int currentPriority) {
        if (currentPriority == CONST_PRIORITY) { // constant priority level
            return parseConst();
        } else if (currentPriority == UNARY_PRIORITY) { // unary priority level
            return parseUnary();
        } else { // binary operations levels
            return parseBinary(currentPriority);
        }
    }

    private MultipleExpression<T> parseBinary(int currentPriority) {
        MultipleExpression<T> currentExpression;
        currentExpression = parseExpression(currentPriority + 1);
        skipWhitespaces();

        outer: while (!currentSource.isEnd()) {
            List<String> list = LEVELS.get(UNARY_PRIORITY - currentPriority - 1);
            for (String op : list) {
                if (checkAndMove(op)) {
                    currentExpression = proceedBinary(
                            op, currentExpression, parseExpression(currentPriority + 1)
                    );
                    continue outer;
                }
            }
            break;
        }
        return currentExpression;
    }

    private MultipleExpression<T> parseUnary() {
        skipWhitespaces();
        if (!checkBetween('a', 'z')) {
            return parseExpression(CONST_PRIORITY);
        } // unary operations except '-'

        String op = parseOperationName();
        switch (op) {
            case "x":
            case "y":
            case "z":
                return new Variable<>(op);
            default:
                return proceedUnary(op, parseExpression(UNARY_PRIORITY));
        }
    }

    private MultipleExpression<T> parseConst() {
        skipWhitespaces();
        if (checkAndMove('(')) { // brackets
            MultipleExpression<T> currentExpression = parseExpression(MIN_PRIORITY);
            expect(')');
            return currentExpression;
        } else if (checkBetween('0', '9')) { // positive constants
            return parseNumber(false);
        } else if (checkAndMove('-')) {
            if (checkBetween('0', '9')) { // negative constants
                return parseNumber(true);
            } else {
                return new Negate<>(parseExpression(UNARY_PRIORITY));
            }
        } else if (currentSource.isEnd()) {
            throw new ParserUnexpectedEndExсeption
                    ("Unexpected end of string", currentSource.getCurrentPos());
        } else {
            throw new ParserUnresolvedSymbolException
                    ("Unresolved symbol '" + currentSource.current() + "'", currentSource.getCurrentPos());
        }
    }

    private MultipleExpression<T> proceedBinary(
            String op, MultipleExpression<T> left, MultipleExpression<T> right
    ) {
        switch (op) {
            case "*":
                return new Multiply<>(left, right);
            case "/":
                return new Divide<>(left, right);
            case "+":
                return new Add<>(left, right);
            case "-":
                return new Subtract<>(left, right);
            case "min":
                return new Minimum<>(left, right);
            case "max":
                return new Maximum<>(left, right);
            default:
                throw new ParserUnresolvedOperationException
                        ("Unknown binary operation '" + op + "'");
        }
    }

    private MultipleExpression<T> proceedUnary(String op, MultipleExpression<T> arg) {
        //noinspection SwitchStatementWithTooFewBranches
        switch (op) {
            case "count":
                return new BitCount<>(arg);
            default:
                throw new ParserUnresolvedOperationException
                        ("Unknown unary operation '" + op + "'");
        }
    }

    private Const<T> parseNumber(boolean sign) {
        StringBuilder result = new StringBuilder(sign ? "-" : "");
        while (checkBetween('0', '9') || check('.', false) ||
                check(',', false) || check('e', false) ||
                check('E', false)) {
            result.append(currentSource.current());
            currentSource.moveNext();
        }
        try {
            return new Const<>(numberParser.parse(result.toString()));
        } catch (NumberFormatException e) {
            throw new ParserConstantFormatException("Invalid constant format: " + result.toString(),
                    e, currentSource.getCurrentPos() - result.length() + 1);
        }
    }

    private String parseOperationName() {
        StringBuilder result = new StringBuilder();
        skipWhitespaces();
        while (checkBetween('a', 'z') || checkBetween('0', '9')) {
            result.append(currentSource.current());
            currentSource.moveNext();
        }
        return result.toString();
    }

    @Override
    protected void expect(char c) {
        if (!check(c)) {
            if (currentSource.isEnd()) {
                throw new ParserUnexpectedEndExсeption
                        ("Unexpected end of string: expected '" + c + "'", currentSource.getCurrentPos());
            }
            throw new ParserUnresolvedSymbolException
                    ("Unresolved symbol '" + currentSource.current() + "': expected '" + c + "'",
                            currentSource.getCurrentPos());
        }
        currentSource.moveNext();
    }
}
