package ru.otus.hw.hw22;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayProcessorTest {

    @Test
    void getElementsAfterLastOne() {
        // Arrange
        int[] testInput1 = {1, 2, 3, 1, 4, 5, 6};
        int[] expectedTest1 = {4, 5, 6};

        int[] testInput2 = {2, 3, 4, 5};
        // Ожидаем: RuntimeException

        int[] test3 = {1, 2, 3, 4, 1};
        int[] expectedTestInput3 = {};

        int[] testInput4 = {1};
        int[] expectedTest4 = {};

        // Act
        ArrayProcessor processor = new ArrayProcessor();

        // Assert
        int[] actualTest1 = processor.getElementsAfterLastOne(testInput1);
        assertArrayEquals(expectedTest1, actualTest1);

        assertThrows(RuntimeException.class, () -> processor.getElementsAfterLastOne(testInput2));

        int[] actualTest3 = processor.getElementsAfterLastOne(test3);
        assertArrayEquals(expectedTestInput3, actualTest3);

        int[] actualTest4 = processor.getElementsAfterLastOne(testInput4);
        assertArrayEquals(expectedTest4, actualTest4);

    }

    @Test
    void containsOnlyOnesAndTwos() {
        // Arrange
        int[] testInput1 = {1, 2};
        boolean expectedTest1 = true;

        int[] testInput2 = {1, 2, 1, 2};
        boolean expectedTest2 = true;

        int[] testInput3 = {1, 1};
        boolean expectedTest3 = false;

        int[] testInput4 = {1, 3};
        boolean expectedTest4 = false;

        int[] testInput5 = {};
        boolean expectedTest5 = false;

        // Act
        ArrayProcessor processor = new ArrayProcessor();

        // Assert
        boolean actualTest1 = processor.containsOnlyOnesAndTwos(testInput1);
        assertEquals(expectedTest1, actualTest1);

        boolean actualTest2 = processor.containsOnlyOnesAndTwos(testInput2);
        assertEquals(expectedTest2, actualTest2);

        boolean actualTest3 = processor.containsOnlyOnesAndTwos(testInput3);
        assertEquals(expectedTest3, actualTest3);

        boolean actualTest4 = processor.containsOnlyOnesAndTwos(testInput4);
        assertEquals(expectedTest4, actualTest4);

        boolean actualTest5 = processor.containsOnlyOnesAndTwos(testInput5);
        assertEquals(expectedTest5, actualTest5);
    }
}