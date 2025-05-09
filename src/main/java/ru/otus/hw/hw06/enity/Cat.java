package ru.otus.hw.hw06.enity;

public class Cat {
    private final String name;
    private final int appetite;

    private boolean satisfied;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.satisfied = false;
    }

    public void eat(Plate plate) {
        if (plate.decreaseFood(appetite)) {
            satisfied = true;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    @Override
    public String toString() {
        return "Cat{" +
            "name='" + name + '\'' +
            ", appetite=" + appetite +
            ", satisfied=" + satisfied +
            '}';
    }
}