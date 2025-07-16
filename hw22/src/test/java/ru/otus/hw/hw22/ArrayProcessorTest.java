package ru.otus.hw.hw22;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты по ArrayProcessor")
class ArrayProcessorTest {

    private ArrayProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new ArrayProcessor();
    }

    @DisplayName("Тесты для метода getElementsAfterLastOne")
    @Nested
    class getElementsAfterLastOneTesting {
        @DisplayName("Тесты для метода getElementsAfterLastOne без Exception")
        @ParameterizedTest
        @MethodSource("getElementsAfterLastOneSource")
        void getElementsAfterLastOne(int[] testInput, int[] expectedOutput) {
            assertArrayEquals(
                expectedOutput,
                processor.getElementsAfterLastOne(testInput),
                "Cодержит элементы исходного массива, идущие после последней единицы"
            );
        }

        private static Stream<Arguments> getElementsAfterLastOneSource() {
            return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 1, 4, 5, 6}, new int[]{4, 5, 6}),
                Arguments.of(new int[]{1, 2, 3, 4, 1}, new int[]{}),
                Arguments.of(new int[]{1}, new int[]{})
            );
        }

        @DisplayName("Тесты для метода getElementsAfterLastOne c Exception")
        @ParameterizedTest
        @MethodSource("getElementsAfterLastOneSourceException")
        void getElementsAfterLastOneException(int[] testInput, Class<Exception> exceptionClass) {
            assertThrows(
                exceptionClass,
                () -> processor.getElementsAfterLastOne(testInput),
                "Если входной массив не содержит единиц, то должно быть брошено RuntimeException."
            );
        }

        private static Stream<Arguments> getElementsAfterLastOneSourceException() {
            return Stream.of(
                Arguments.of(new int[]{2}, RuntimeException.class),
                Arguments.of(new int[]{}, RuntimeException.class)
            );
        }
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