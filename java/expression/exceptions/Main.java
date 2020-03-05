package expression.exceptions;

import expression.CommonExpression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommonExpression expression = new ExpressionParser().parse("log2x");
        System.err.println(String.format("%-16s%s", "Input x", "Output/verdict"));
        for (int i = -10; i <= 10; i++) {
            System.out.print(String.format("%-16d", i));
            try {
                System.out.println(expression.evaluate(i, 0, 0));
            } catch (ParserException | ExpressionCalculateException e) {
                System.out.println("Exception! " + e.getMessage());
            }
        }
        System.err.println("Enter the expression to parse in the first line and x, y, z in the second:");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String line = sc.nextLine();
            try {
                expression = new ExpressionParser().parse(line);
                int x = sc.nextInt(), y = sc.nextInt(), z = sc.nextInt();
                sc.nextLine();
                try {
                    System.out.println(" " + expression.evaluate(x, y, z));
                } catch (ExpressionCalculateException e) {
                    System.err.println("Calculating exception! " + e.getMessage());
                }
            } catch (ParserException e) {
                System.err.println("Parsing exception! " + e.getMessage());
            }
        }
    }
}
