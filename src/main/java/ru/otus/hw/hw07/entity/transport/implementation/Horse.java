package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementConsumption;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;
import java.util.Map;

/** Класс Лошадь */
public class Horse extends TerrainMovementConsumption implements Transport {

    @Getter
    private final String name;

    public Horse(String name, double initialEnergy, double energyConsumption) {
        super(
            initialEnergy,
            energyConsumption,
            Map.of(),
            List.of(TerrainType.SWAMP)
        );

        this.name = name;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Лошадь " + name + " не может передвигаться по " + terrain);
            return false;
        }

        boolean moved = super.move(distance, terrain);

        if (moved) {
            System.out.println("Лошадь " + name + " проскакала " + distance + " км по " + terrain + ". Осталось энергии: " + currentResourceValue);
        } else {
            System.out.println("Лошадь " + name + " слишком устала для поездки. Осталось энергии: " + currentResourceValue);
        }

        return moved;
    }

    public void rest(double hours) {
        double recoveredEnergy = hours * 20; // 20 единиц энергии за час отдыха
        currentResourceValue += recoveredEnergy;
        System.out.println("Лошадь " + name + " отдохнула. Восстановлено энергии: " + recoveredEnergy +
            ". Текущая энергия: " + currentResourceValue);
    }
}

