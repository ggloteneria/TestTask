import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Calculator {
    public Calculator() {
    }

    public String parseAndCalc(String numbBeforeOperator, String numbAfterOperator,
                               String operator, String[] arr) {
        int firstNumber;
        int secondNumber;
        String result;

        checkRightFormatOfExpression(arr);
        checkRightNumberSystemOfExpression(numbBeforeOperator, numbAfterOperator);

        if (isNumeric(numbBeforeOperator) && isNumeric(numbAfterOperator)) {
            firstNumber = Integer.parseInt(numbBeforeOperator);
            secondNumber = Integer.parseInt(numbAfterOperator);
            checkNumbersForCorrectValue(firstNumber, secondNumber);
            result = String.valueOf(doCalc(operator, firstNumber, secondNumber));
        } else {
            firstNumber = convertRomanToArab(numbBeforeOperator);
            secondNumber = convertRomanToArab(numbAfterOperator);
            checkNumbersForCorrectValue(firstNumber, secondNumber);
            if (doCalc(operator, firstNumber, secondNumber) < 0) {
                throw new IncorrectValueException("В римской системе счисления не может быть отрицательных чисел");
            }
            result = convertArabToRoman(doCalc(operator, firstNumber, secondNumber));
        }
        return result;
    }

    public int doCalc(String operator, int firstNumber, int secondNumber) {
        return switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "/" -> firstNumber / secondNumber;
            default -> throw new IncorrectOperatorException("Данная операция отсутствует");
        };
    }

    public void checkNumbersForCorrectValue(int firstNumber, int secondNumber) {
        if (firstNumber > 10 || firstNumber < 1 || secondNumber > 10 || secondNumber < 1) {
            throw new IncorrectValueException("Число не входит в диапозон от 1 до 10 (включительно)");
        }
    }

    public boolean isNumeric(String string) {
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    public int convertRomanToArab(String numRoman) {
        String[] arr = numRoman.replace(" ", "").split("");
        int value = 0;

        List<RomanNumeral> romanNumerals = new ArrayList<>();
        for (String str : arr) {
            romanNumerals.add(RomanNumeral.valueOf(str));
        }
        while (!romanNumerals.isEmpty()) {
            RomanNumeral current = romanNumerals.remove(0);
            if (!romanNumerals.isEmpty() && current.isNextValueGreater(romanNumerals.get(0))) {
                value += current.doProcessRomanNumber(romanNumerals.remove(0));
            } else {
                value += current.getValue();
            }
        }
        return value;
    }

    public String convertArabToRoman(Integer numArabian) {
        String[] roman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
                "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII",
                "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII",
                "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI",
                "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV",
                "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII",
                "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII",
                "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        };
        return roman[numArabian];
    }

    public void checkRightNumberSystemOfExpression(String numbBeforeOperator, String numbAfterOperator) {
        if ((!isNumeric(numbBeforeOperator) && isNumeric(numbAfterOperator)) ||
                (isNumeric(numbBeforeOperator) && !isNumeric(numbAfterOperator))) {
            throw new IncorrectExpressionFormatException("В выражении не могут участвовать две разных системы счисления");
        }
    }

    public void checkRightFormatOfExpression(String[] arrayOfNumbers) {
        if (Arrays.stream(arrayOfNumbers).filter(x -> isOperator(x.charAt(0))).count() >= 2) {
            throw new IncorrectExpressionFormatException("Не соответствует формату 2 операнда 1 оператор");
        }
    }

    public boolean isMathematicalOperation(String[] arrayOfNumbers) {
        if (Arrays.stream(arrayOfNumbers).noneMatch(x -> isOperator(x.charAt(0)))) {
            throw new IncorrectExpressionFormatException("Не является математической операцией");
        }
        return true;
    }

    public boolean isOperator(Character character){
        return !Character.isLetterOrDigit(character);
    }
}
