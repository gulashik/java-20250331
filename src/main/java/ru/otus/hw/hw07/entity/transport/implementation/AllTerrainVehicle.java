package ru.otus.hw.hw07.entity.transport.implementation;

import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.Transport;

/**
 * Класс Вездеход
 */
public class AllTerrainVehicle implements Transport {
    private final String name;
    private final double fuelConsumption; // расход топлива на 1 км

    private double fuel;

    public AllTerrainVehicle(String name, double initialFuel, double fuelConsumption) {
        this.name = name;
        this.fuel = initialFuel;
        this.fuelConsumption = fuelConsumption;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        // Вездеход потребляет больше топлива на сложной местности
        double terrainMultiplier = 1.0;
        if (terrain == TerrainType.DENSE_FOREST) {
            terrainMultiplier = 2.0;
        } else if (terrain == TerrainType.SWAMP) {
            terrainMultiplier = 3.0;
        }

        double requiredFuel = distance * fuelConsumption * terrainMultiplier;
        if (fuel < requiredFuel) {
            System.out.println("Недостаточно топлива для поездки на вездеходе. Осталось: " + fuel + " л.");
            return false;
        }

        fuel -= requiredFuel;
        System.out.println("Вездеход " + name + " проехал " + distance + " км по " + terrain +
            ". Осталось топлива: " + fuel + " л.");
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean cantMoveOn(TerrainType terrain) {
        // Вездеход может перемещаться по любой местности
        return true;
    }

    public void refuel(double amount) {
        fuel += amount;
        System.out.println("Вездеход " + name + " заправлен. Текущий запас топлива: " + fuel + " л.");
    }
}
