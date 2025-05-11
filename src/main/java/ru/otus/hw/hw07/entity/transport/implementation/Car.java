package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementConsumption;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;
import java.util.Map;

/**
 * Класс представляет транспортное средство типа "Машина".
 * Машина может передвигаться по определённым типам местности и расходует топливо
 * в зависимости от пройденного расстояния.
 * Машина не способна передвигаться по густому лесу и болотам.
 *
 * Реализует интерфейс Transport
 *
 * @see Transport
 */
public class Car extends TerrainMovementConsumption implements Transport {

    @Getter
    private final String name;

    /**
     * Создает новый экземпляр машины с указанными параметрами.
     *
     * @param name имя или модель машины
     * @param initialFuel начальное количество топлива в литрах
     * @param fuelConsumption расход топлива в литрах на 1 км пути
     */
    public Car(String name, double initialFuel, double fuelConsumption) {
        super(
            initialFuel,
            fuelConsumption,
            Map.of(),
            List.of(TerrainType.SWAMP, TerrainType.DENSE_FOREST)
        );

        this.name = name;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Машина " + name + " не может передвигаться по " + terrain);
            return false;
        }

        boolean moved = super.move(distance, terrain);

        if (moved) {
            System.out.println("Машина " + name + " проехала " + distance + " км по " + terrain + ". Осталось топлива: " + currentResourceValue + " л.");
        } else {
            System.out.println("Недостаточно топлива для поездки. Осталось: " + currentResourceValue + " л.");
        }

        return moved;
    }

    public void refuel(double amount) {
        currentResourceValue += amount;
        System.out.println("Машина " + name + " заправлена. Текущий запас топлива: " + currentResourceValue + " л.");
    }
}