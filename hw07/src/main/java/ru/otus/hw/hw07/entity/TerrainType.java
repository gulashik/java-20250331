package ru.otus.hw.hw07.entity;

/** Перечисление типов местности */
public enum TerrainType {
    DENSE_FOREST("Густой лес"),
    PLAIN("Равнина"),
    SWAMP("Болото");

    private final String name;

    TerrainType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
