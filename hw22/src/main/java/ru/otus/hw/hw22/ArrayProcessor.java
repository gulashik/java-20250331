package ru.otus.hw.hw22;

import java.util.Arrays;

public class ArrayProcessor {

    private final static int EMPTY_TAG = -1;

    public int[] getElementsAfterLastOne(int[] array) {
        if (array == null || array.length == 0) {
            throw new RuntimeException("Массив не может быть null или пустым");
        }

        int lastOneIndex = EMPTY_TAG;
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 1) {
                lastOneIndex = i;
                break;
            }
        }

        if (lastOneIndex == EMPTY_TAG) {
            throw new RuntimeException("Массив не содержит единиц");
        }

        return Arrays.copyOfRange(array, lastOneIndex + 1, array.length);
    }

    public boolean containsOnlyOnesAndTwos(int[] array) {
        if (array == null || array.length == 0) {
            return false;
        }

        boolean hasOne = false;
        boolean hasTwo = false;

        for (int element : array) {
            if (element == 1) {
                hasOne = true;
            } else if (element == 2) {
                hasTwo = true;
            } else {
                // Найден элемент, отличный от 1 и 2
                return false;
            }
        }

        // Возвращаем true только если есть и единицы, и двойки
        return hasOne && hasTwo;
    }

}

