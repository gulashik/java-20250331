package ru.otus.hw.hw08;

import static ru.otus.hw.hw08.util.ArrayUtils.sum;

public class Main {

    public static void main(String[] args) {
        String[][] correctArray = {
            {"1", "2", "3", "4"},
            {"5", "6", "7", "8"},
            {"9", "10", "11", "12"},
            {"13", "14", "15", "16"}
        };

        String[][] incorrectArraySize = {
            {"1", "2", "3", "4"},
        };

        String[][] incorrectArrayChar = {
            {"1", "2", "3", "4"},
            {"5", "6", "7", "8"},
            {"9", "10", "11", "12"},
            {"13", "14", "15", "q"}
        };


        sum(correctArray);
        sum(incorrectArraySize);
        sum(incorrectArrayChar);
    }
}
