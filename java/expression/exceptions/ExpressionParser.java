package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringSource;

import java.util.List;

public class ExpressionParser extends BaseParser implements Parser {
    private static final int UNARY_PRIORITY = 0;
    private static final List<List<String>> LEVELS = List.of(
            List.of("**", "//"),
            List.of("*", "/"),
            List.of("+", "-")
    );
    private static final int MIN_PRIORITY = UNARY_PRIORITY - LEVELS.size();
    private static final int CONST_PRIORITY = UNARY_PRIORITY + 1;

    @Override
    public CommonExpression parse(String expression) {
        currentSource = new StringSource(expression);
        MultipleExpression result = parseExpression(MIN_PRIORITY);
        if (!currentSource.isEnd()) {
            throw new ParserUnresolvedSymbolException
                    ("Unresolved symbol '" + currentSource.current() + "'", currentSource.getCurrentPos());
        }
        return result;
    }

    private MultipleExpression parseExpression(int currentPriority) {
        if (currentPriority == CONST_PRIORITY) { // constant priority level
            return parseConst();
        } else if (currentPriority == UNARY_PRIORITY) { // unary priority level
            return parseUnary();
        } else { // binary operations levels
            return parseBinary(currentPriority);
        }
    }

    private MultipleExpression parseBinary(int currentPriority) {
        MultipleExpression currentExpression;
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

    private MultipleExpression parseUnary() {
        skipWhitespaces();
        if (!checkBetween('a', 'z')) {
            return parseExpression(CONST_PRIORITY);
        } // unary operations except '-'

        String op = parseOperationName();
        switch (op) {
            case "x":
            case "y":
            case "z":
                return new Variable(op);
            default:
                return proceedUnary(op, parseExpression(UNARY_PRIORITY));
        }

    }

    private MultipleExpression parseConst() {
        skipWhitespaces();
        if (checkAndMove('(')) { // brackets
            MultipleExpression currentExpression = parseExpression(MIN_PRIORITY);
            expect(')');
            return currentExpression;
        } else if (checkBetween('0', '9')) { // positive constants
            return parseConstant(false);
        } else if (checkAndMove('-')) {
            if (checkBetween('0', '9')) { // negative constants
                return parseConstant(true);
            } else {
                return new CheckedNegate(parseExpression(UNARY_PRIORITY));
            }
        } else if (currentSource.isEnd()) {
            throw new ParserUnexpectedEndExсeption
                    ("Unexpected end of string", currentSource.getCurrentPos());
        } else {
            throw new ParserUnresolvedSymbolException
                    ("Unresolved symbol '" + currentSource.current() + "'", currentSource.getCurrentPos());
        }
    }

    private MultipleExpression proceedBinary(String op, MultipleExpression left, MultipleExpression right) {
        switch (op) {
            case "**":
                return new CheckedPow(left, right);
            case "//":
                return new CheckedLog(left, right);
            case "*":
                return new CheckedMultiply(left, right);
            case "/":
                return new CheckedDivide(left, right);
            case "+":
                return new CheckedAdd(left, right);
            case "-":
                return new CheckedSubtract(left, right);
            default:
                throw new ParserUnresolvedOperationException
                        ("Unknown binary operation '" + op + "'");
        }
    }

    private MultipleExpression proceedUnary(String op, MultipleExpression arg) {
        switch (op) {
//            case "-": // calculates apart
//                return new CheckedNegate(arg);
            case "log2":
                return new CheckedLog2(arg);
            case "pow2":
                return new CheckedPow2(arg);
            default:
                throw new ParserUnresolvedOperationException
                        ("Unknown unary operation '" + op + "'");
        }
    }

    private Const parseConstant(boolean sign) {
        StringBuilder result = new StringBuilder(sign ? "-" : "");
        while (checkBetween('0', '9')) {
            result.append(currentSource.current());
            currentSource.moveNext();
        }
        try {
            return new Const(Integer.parseInt(result.toString()));
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
