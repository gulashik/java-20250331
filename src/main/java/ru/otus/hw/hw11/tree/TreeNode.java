package ru.otus.hw.hw11.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Представляет узел в бинарном дереве поиска.
 * Каждый узел содержит значение и ссылки на левый и правый дочерние узлы.
 */
@Getter
@Setter
@AllArgsConstructor
public class TreeNode<T extends Comparable<T>> {
    private T value;
    private TreeNode<T> left;
    private TreeNode<T> right;

    public TreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}