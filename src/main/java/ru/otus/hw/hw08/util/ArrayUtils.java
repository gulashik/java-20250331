package ru.otus.hw.hw08.util;

import ru.otus.hw.hw08.exception.AppArrayDataException;
import ru.otus.hw.hw08.exception.AppArraySizeException;

import java.util.Arrays;

public abstract class ArrayUtils {
    public static void sum(String[][] correctArray) {
        try {
            int result = validateAndSum(correctArray);
            System.out.println("Sum of array elements: " + result);
        } catch (Exception e) {
            System.out.printf(
                "Have got an exception: %s cause '%s' %n",
                e.getClass().getSimpleName(),
                e.getMessage() == null ? "no message" : e.getMessage()
            );
        }
    }

    private static int validateAndSum(String[][] array) {
        return Arrays
            .stream(validateArray(array))
            .flatMap(Arrays::stream)
            .mapToInt(ArrayUtils::validateArrayItem)
            .sum();
    }

    private static String[][] validateArray(String[][] array) {
        if (array.length != 4 || array[0].length != 4) {
            throw new AppArraySizeException("Array size must be 4x4");
        }
        return array;
    }

    private static int validateArrayItem(String item) {
        try {
            return Integer.parseInt(item);
        } catch (NumberFormatException e) {
            throw new AppArrayDataException("Array items must be a number");
        }
    }
}
