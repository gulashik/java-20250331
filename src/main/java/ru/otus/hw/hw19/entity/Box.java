package ru.otus.hw.hw19.entity;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public double getWeight() {
        double totalWeight = 0.0;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        return totalWeight;
    }

    public boolean compare(Box<?> otherBox) {
        return Math.abs(this.getWeight() - otherBox.getWeight()) < 0.0001;
    }

    public void transferTo(Box<T> otherBox) {
        if (otherBox == null) {
            throw new IllegalArgumentException("Целевая коробка не может быть null");
        }
        otherBox.fruits.addAll(this.fruits);
        this.fruits.clear();
    }

    public int countFruits() {
        return fruits.size();
    }
}
