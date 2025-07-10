package ru.otus.hw.hw11;

import ru.otus.hw.hw11.tree.SearchTreeImpl;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    final static int MAX_ELEMENTS_IN_TREE = 10;
    final static boolean USE_ITERATIVE = true;

    public static void main(String[] args) {

        List<Integer> list = Stream.iterate(1, i -> i <= MAX_ELEMENTS_IN_TREE, i -> i + 1).toList();
        SearchTreeImpl<Integer> tree = new SearchTreeImpl<>(list);

        System.out.println("Поиск элемента 5: " + tree.find(5, USE_ITERATIVE));
        System.out.println("Поиск элемента 11: " + tree.find(11, USE_ITERATIVE));

        System.out.println("Отсортированный список из дерева: " + tree.getSortedList(USE_ITERATIVE));
    }
}