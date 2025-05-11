package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementConsumption;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;
import java.util.Map;

/**
 * Класс Вездеход
 */
public class AllTerrainVehicle extends TerrainMovementConsumption implements Transport {
    @Getter
    private final String name;

    public AllTerrainVehicle(String name, double initialFuel, double fuelConsumption) {
        super(
            initialFuel,
            fuelConsumption,
            Map.of(TerrainType.DENSE_FOREST, 2, TerrainType.SWAMP, 3),
            List.of()
        );

        this.name = name;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        boolean moved = super.move(distance, terrain);

        if (moved) {
            System.out.println("Вездеход " + name + " проехал " + distance + " км по " + terrain + ". Осталось топлива: " + currentResourceValue + " л.");
        } else {
            System.out.println("Недостаточно топлива для поездки на вездеходе. Осталось: " + currentResourceValue + " л.");
        }

        return moved;
    }

    @Override
    public boolean cantMoveOn(TerrainType terrain) {
        // Вездеход может перемещаться по любой местности
        return true;
    }

    public void refuel(double amount) {
        currentResourceValue += amount;
        System.out.println("Вездеход " + name + " заправлен. Текущий запас топлива: " + currentResourceValue + " л.");
    }
}
