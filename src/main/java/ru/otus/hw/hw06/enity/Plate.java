package ru.otus.hw.hw06.enity;

public class Plate {
    private final int capacity;
    private int food;

    public Plate(int capacity) {
        this.capacity = capacity;
        this.food = capacity;
    }

    public boolean decreaseFood(int amount) {
        if (food >= amount) {
            food -= amount;
            return true;
        }
        return false;
    }

    public void addFood(int amount) {
        food = Math.min(capacity, food + amount);
    }
}
