package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Expression exp = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        );
        System.out.println(exp.toString());
        Scanner sc = new Scanner(System.in);
        int val = sc.nextInt();
        System.out.println(exp.evaluate(val));
    }
}
