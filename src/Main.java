import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String expression = scanner.nextLine();
        System.out.println(calc(expression));

    }

    public static String calc(String input) {
        Calculator calculator = new Calculator();
        String result = null;

        String inputWithoutSpace = input.replace(" ", "");
        String[] arrayOfNumbers = inputWithoutSpace.split("");

        if (calculator.isMathematicalOperation(arrayOfNumbers)){
            for (int i = 0; i < arrayOfNumbers.length; i++) {
                if (calculator.isOperator(arrayOfNumbers[i].charAt(0))) {
                    String numbBeforeOperator = inputWithoutSpace.substring(0, i);
                    String numbAfterOperator = inputWithoutSpace.substring(i + 1, arrayOfNumbers.length);
                    String operator = String.valueOf(arrayOfNumbers[i]);

                    result = calculator.parseAndCalc(numbBeforeOperator, numbAfterOperator, operator, arrayOfNumbers);
                }
            }
        }
        return result;
    }

}
