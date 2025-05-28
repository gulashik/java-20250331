package ru.otus.hw.hw11;

import ru.otus.hw.hw11.tree.SearchTreeImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SearchTreeImpl<Integer> tree = new SearchTreeImpl<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println("Поиск элемента 5: " + tree.find(5));
        System.out.println("Поиск элемента 11: " + tree.find(11));

        System.out.println("Отсортированный список из дерева: " + tree.getSortedList());
    }
}