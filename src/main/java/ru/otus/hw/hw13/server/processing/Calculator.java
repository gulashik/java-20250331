package ru.otus.hw.hw13.server.processing;

import java.util.Set;

/**
 * Калькулятор для выполнения базовых математических операций.
 */
public final class Calculator {
    private Calculator() {}

    private static final Set<String> SUPPORTED_OPERATIONS = Set.of("+", "-", "*", "/");

    /**
     * Возвращает строку с доступными операциями.
     *
     * @return строка доступных операций
     */
    public static String getAvailableOperations() {
        return "Доступные операции: " + String.join(", ", SUPPORTED_OPERATIONS);
    }

    /**
     * Обрабатывает математическое выражение от клиента.
     *
     * @param input строка с выражением в формате "число операция число"
     * @return результат вычисления или сообщение об ошибке
     */
    public static String processCalculation(String input) {
        try {
            String[] parts = input.trim().split("\\s+");

            if (parts.length != 3) {
                return "Ошибка: неверный формат. Используйте: число операция число";
            }

            double num1 = Double.parseDouble(parts[0]);
            String operation = parts[1];
            double num2 = Double.parseDouble(parts[2]);

            double result = calculate(num1, operation, num2);
            return "Результат: " + num1 + " " + operation + " " + num2 + " = " + result;

        } catch (NumberFormatException e) {
            return "Ошибка: неверный формат числа. Проверьте введенные числа.";
        } catch (Exception e) {
            return "Ошибка обработки: " + e.getMessage();
        }
    }
    /**
     * Выполняет математическую операцию над двумя числами.
     *
     * @param num1 первое число
     * @param operation операция (+, -, *, /)
     * @param num2 второе число
     * @return результат вычисления
     * @throws IllegalArgumentException если операция не поддерживается
     * @throws ArithmeticException при делении на ноль
     */
    private static double calculate(double num1, String operation, double num2) {

        return switch (operation.trim()) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль невозможно");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operation);
        };
    }
}

