package ru.otus.hw.hw07.entity.transport.implementation;

import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementLimit;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;

/** Класс Велосипед */
public class Bicycle extends TerrainMovementLimit implements Transport {
    private final String name;

    public Bicycle(String name) {
        super(List.of(TerrainType.SWAMP));
        this.name = name;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Велосипед " + name + " не может передвигаться по " + terrain);
            return false;
        }

        System.out.println("Велосипед " + name + " проехал " + distance + " км по " + terrain);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

}
