package expression.parser;

import expression.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TripleExpression expression = new ExpressionParser().parse("-(-(-\t\t-5 + 16   *x*y) + 1 * z) -(((-11)))");
        Scanner in = new Scanner(System.in);
        System.out.println(expression.evaluate(0, 0, 0));
//        while (true) {
//            int x = in.nextInt(), y = in.nextInt(), z = in.nextInt();
//            System.out.println(" " + expression.evaluate(x, y, z));
//        }
    }
}
