package ru.otus.hw.hw11.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сбалансированном дерева из отсортированного списка элементов
 * и методов поиска по нему.
 *
 * @see SearchTree
 * @see TreeNode
 */

public class SearchTreeImpl<T extends Comparable<T>> implements SearchTree<T> {
    /**
     * Корневой узел дерева.
     */
    private final TreeNode<T> root;

    /**
     * @param sortedList предварительно отсортированный список
     */
    public SearchTreeImpl(List<T> sortedList) {
        if (sortedList == null || sortedList.isEmpty()) {
            root = null;
        } else {
            root = buildBalancedTree(sortedList, 0, sortedList.size() - 1);
        }
    }

    /**
     * Рекурсивное создание сбалансированного дерева из отсортированного списка.
     *
     * @param sortedList отсортированный список
     * @param start      начальный индекс
     * @param end        конечный индекс
     * @return корневой узел
     */
    private TreeNode<T> buildBalancedTree(List<T> sortedList, int start, int end) {
        if (start > end) {
            return null;
        }

        // Средний элемент - корень
        int mid = start + (end - start) / 2;
        TreeNode<T> treeNode = new TreeNode<>(sortedList.get(mid));

        // Рекурсивно создаём левое и правое поддерево
        treeNode.setLeft(buildBalancedTree(sortedList, start, mid - 1));
        treeNode.setRight(buildBalancedTree(sortedList, mid + 1, end));

        return treeNode;
    }

    /**
     * Поиск элемента в дереве.
     *
     * @param element элемент для поиска
     * @return найденный элемент или null, если элемент не найден
     */
    @Override
    public T find(T element) {
        //return findRecursive(root, element);
        return findIterative(root, element);
    }

    /**
     * Рекурсивный метод поиска элемента в дереве.
     *
     * @param treeNode текущий узел
     * @param element  элемент для поиска
     * @return найденный элемент или null, если элемент не найден
     */
    private T findRecursive(TreeNode<T> treeNode, T element) {
        if (treeNode == null) {
            return null;
        }

        int compareResult = element.compareTo(treeNode.getValue());

        if (compareResult == 0) {
            return treeNode.getValue(); // Элемент найден
        } else if (compareResult < 0) {
            return findRecursive(treeNode.getLeft(), element); // Ищем в левом поддереве
        } else {
            return findRecursive(treeNode.getRight(), element); // Ищем в правом поддереве
        }
    }

    /**
     * Итеративный метод поиска элемента в дереве.
     *
     * @param root корневой узел для начала поиска
     * @param element элемент для поиска
     * @return найденный элемент или null, если элемент не найден
     */

    private T findIterative(TreeNode<T> root, T element) {
        TreeNode<T> current = root;

        while (current != null) {
            int compareResult = element.compareTo(current.getValue());

            if (compareResult == 0) {
                return current.getValue(); // Элемент найден
            } else if (compareResult < 0) {
                current = current.getLeft(); // Идем в левое поддерево
            } else {
                current = current.getRight(); // Идем в правое поддерево
            }
        }

        return null; // Элемент не найден
    }

    /**
     * Возвращает отсортированный список элементов дерева.
     *
     * @return отсортированный список
     */
    @Override
    public List<T> getSortedList() {
        List<T> result = new ArrayList<>();
        //getSortedListRecursive(root, result);
        getSortedListIterative(root, result);
        return result;
    }

    /**
     * Обход дерева в порядке сортировки.
     * Упёрто из интернета, сам бы до такого не додумался. )))
     *
     * @param treeNode текущий узел
     * @param result   список для сохранения результатов
     */
    private void getSortedListRecursive(TreeNode<T> treeNode, List<T> result) {

        if (treeNode != null) {
            // вначале обходим левое(меньшее) поддерево; потом будет скипаться
            getSortedListRecursive(treeNode.getLeft(), result);

            result.add(treeNode.getValue()); // для сохранения порядка(что потом не сортировать)

            // потом обходим правое(большее) поддерево
            getSortedListRecursive(treeNode.getRight(), result);
            //    5
            //   / \
            //  3   7
            // / \ / \
            //2  4 6  8
        }
    }

    private void getSortedListIterative(TreeNode<T> root, List<T> result) {
        if (root == null) {
            return;
        }

        java.util.Deque<TreeNode<T>> stack = new java.util.ArrayDeque<>();
        TreeNode<T> current = root;

        // Главный цикл продолжается, пока есть узлы для обработки
        while (true) {
            // Цикл 1: идем влево до конца, добавляя узлы в стек
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }

            // Если стек пуст, работа завершена
            if (stack.isEmpty()) {
                break;
            }

            // Цикл 2: обрабатываем узел и переходим вправо
            current = stack.pop();
            result.add(current.getValue());
            current = current.getRight();
        }
    }
}