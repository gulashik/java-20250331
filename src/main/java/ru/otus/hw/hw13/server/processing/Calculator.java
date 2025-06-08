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
     * Выполняет математическую операцию над двумя числами.
     *
     * @param num1 первое число
     * @param operation операция (+, -, *, /)
     * @param num2 второе число
     * @return результат вычисления
     * @throws IllegalArgumentException если операция не поддерживается
     * @throws ArithmeticException при делении на ноль
     */
    public static double calculate(double num1, String operation, double num2) {

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

