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

    @DisplayName("Группа тесты для метода getElementsAfterLastOne")
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

         static Stream<Arguments> getElementsAfterLastOneSource() {
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

         static Stream<Arguments> getElementsAfterLastOneSourceException() {
            return Stream.of(
                Arguments.of(new int[]{2}, RuntimeException.class),
                Arguments.of(new int[]{}, RuntimeException.class)
            );
        }
    }

    @DisplayName("Группа тесты для метода containsOnlyOnesAndTwos")
    @Nested
    class containsOnlyOnesAndTwosTesting {
        @DisplayName("Тесты для метода containsOnlyOnesAndTwos")
        @ParameterizedTest
        @MethodSource("containsOnlyOnesAndTwosSource")
        void containsOnlyOnesAndTwos(int[] testInput, boolean expectedOutput) {
            assertEquals(expectedOutput,
                processor.containsOnlyOnesAndTwos(testInput),
                "Входной массив состоит только из чисел 1 и 2"
            );
        }

        static Stream<Arguments> containsOnlyOnesAndTwosSource() {
            return Stream.of(
                Arguments.of(new int[]{1, 2}, true),
                Arguments.of(new int[]{1, 2, 1, 2}, true),
                Arguments.of(new int[]{1, 1}, false),
                Arguments.of(new int[]{1, 3}, false),
                Arguments.of(new int[]{}, false)
            );
        }
    }
}