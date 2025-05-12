package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Builder;
import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementLimit;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;

/**
 * Класс Велосипед представляет собой транспортное средство,
 * которое может передвигаться по определённым типам местности не расходуя энергии.
 * 
 * @see Transport
 * @see TerrainMovementLimit
 * @see TerrainType
 */
public class Bicycle extends TerrainMovementLimit implements Transport {

    @Getter
    private final String name;

    /**
     * Создает новый экземпляр велосипеда с указанным названием.
     * По умолчанию велосипед не может передвигаться по болоту.
     * 
     * @param name название велосипеда
     */
    @Builder
    public Bicycle(String name) {
        super(List.of(TerrainType.SWAMP));
        this.name = name;
    }

    /**
     * Перемещает велосипед на указанное расстояние по заданной местности.
     * 
     * @param distance расстояние в километрах, которое нужно преодолеть
     * @param terrain тип местности, по которой происходит перемещение
     * @return true, если перемещение успешно выполнено, false - если перемещение невозможно
     */
    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Велосипед " + name + " не может передвигаться по " + terrain);
            return false;
        }

        System.out.println("Велосипед " + name + " проехал " + distance + " км по " + terrain);
        return true;
    }
}